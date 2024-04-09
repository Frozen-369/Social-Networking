package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.CustomFriendDao;
import com.project.socialnetwork.dao.FriendDao;

import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.CustomFriends;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.RequestStaus;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FriendService {


    private final FriendDao friendDao;

    private final UserDao userDao;

    private  final CustomFriendDao customFriends;

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    public FriendService(FriendDao friendDao, UserDao userDao, CustomFriendDao customFriends) {
        this.friendDao = friendDao;
        this.userDao = userDao;
        this.customFriends = customFriends;
    }

    public List<FriendsList> getAllFriends(User userId) {
        if (userId != null) {
            return customFriends.findBySender(userId);
        }
        return List.of();
    }

    @Transactional
    public void unFriend(User user, User friend) {
        if (areFriends(user, friend)) {
            CustomFriends friendship = customFriends.findBySenderAndReciverId(user, friend);
            if (friendship != null) {
                customFriends.delete(friendship);
                friendDao.deleteByFriend(friend);
            }
            user.removeFriend(friend);
            friend.removeFriend(user);
            logger.info("User with ID {} unfriended user with ID {}", user.getUserId(), friend.getUserId());
        } else {
            logger.warn("User with ID {} attempted to unfriend user with ID {}, but they are not friends", user.getUserId(), friend.getUserId());
        }
    }

    private boolean areFriends(User user, User friend) {
        return customFriends.existsBySenderAndReciverId(user, friend)
                || customFriends.existsByReciverIdAndSender(friend, user);
    }

    public void sendFriendRequest(User user, User friend) {
        if (user == null || friend == null) {
            logger.error("User or friend is null");
            ResponseEntity.badRequest().body(ResponseUtils.USER_EMPTY);
            return;
        }

        if (areFriends(user, friend)) {
            ResponseEntity.badRequest().body(ResponseUtils.ALREADY_FRIENDS);
            return;
        }

        CustomFriends friendship = new CustomFriends();
        friendship.setReciverId(friend);
        friendship.setSender(user);
        friendship.setRequestStatus(RequestStaus.PENDING);
        friendship.setFriendSince(LocalDate.now());
        customFriends.save(friendship);

        FriendsList friendsList=new FriendsList();
        friendsList.setFriend(friend);
        friendsList.setDate(LocalDate.now());
        friendsList.setRequestStatus(RequestStaus.PENDING);

        friendDao.save(friendsList);

        ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }

    public void acceptFriendRequest(User user, User friend) {
        CustomFriends friendship = customFriends.findBySenderAndReciverId(user, friend);
        if (friendship == null) {
            ResponseEntity.badRequest().body(ResponseUtils.FRIEND_REQUEST_NOT_FOUND);
            return;
        } else {
            FriendsList friendsList = new FriendsList();
            friendsList.setFriend(friend);
            friendsList.setRequestStatus(RequestStaus.ACCEPTED);
            friendsList.setDate(LocalDate.now());
            friendDao.save(friendsList);

            friendship.setRequestStatus(RequestStaus.ACCEPTED);
            friendship.setFriendSince(LocalDate.now());
            customFriends.save(friendship);
        }
        ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_ACCEPTED);
    }

}
