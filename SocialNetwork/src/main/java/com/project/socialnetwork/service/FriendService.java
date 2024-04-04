package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.CustomFriendDao;
import com.project.socialnetwork.dao.FriendDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.CustomFriends;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.RequestStaus;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    private final FriendDao friendDao;

    private final UserDao userDao;

    private  final CustomFriendDao customFriends;

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    public FriendService(FriendDao friendDao, UserDao userDao,CustomFriendDao customFriends) {
        this.friendDao = friendDao;
        this.userDao = userDao;
        this.customFriends = customFriends;
    }

    public List<FriendsList> getAllFriends(Long userId) {
        User user = userDao.findById(userId).orElse(null);
        if (user != null) {
            return user.getFriends();
        }
        return List.of();
    }
    public void unFriend(User user, User friend) {
        if (areFriends(user, friend)) {
            customFriends.deletBySenderAndFriend(user, friend);
            friendDao.deleteByFriend(friend.getFriends());
            logger.info("User with ID {} unfriended user with ID {}", user.getUserId(), friend.getFriends());
        } else {
            logger.warn("User with ID {} attempted to unfriend user with ID {}, but they are not friends", user.getUserId(), friend.getFriends());
        }
    }

    public ResponseEntity<String> sendFriendRequest(User user, User friend) {
        if (user == null || friend == null) {
            logger.error("User or friend is null");
            return ResponseEntity.badRequest().body(ResponseUtils.USER_EMPTY);
        }

        if (areFriends(user, friend)) {
            return ResponseEntity.badRequest().body(ResponseUtils.ALREADY_FRIENDS);
        }

        CustomFriends friendship = new CustomFriends();
        friendship.setFriend(friend);
        friendship.setSender(user);
        friendship.setRequestStatus(RequestStaus.PENDING);
        friendship.setDate(LocalDate.now());

        friendDao.save(friendship);
        customFriends.save(friendship);

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }

    public ResponseEntity<String> acceptFriendRequest(User user, User friend) {
        CustomFriends friendship = customFriends.existsBySenderAndFriend(friend, user);

        if (friendship == null) {
            return ResponseEntity.badRequest().body(ResponseUtils.FRIEND_REQUEST_NOT_FOUND);
        }

        friendship.setRequestStatus(RequestStaus.ACCEPTED);
        friendship.setDate(LocalDate.now());

        friendDao.save(friendship);
        customFriends.save(friendship);

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_ACCEPTED);
    }

    private boolean areFriends(User user, User friend) {
        return customFriends.existsBysenderAndfriends(user, friend)
                || customFriends.existsByfriendsAndsender(friend, user);
    }

}
