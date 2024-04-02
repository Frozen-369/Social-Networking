package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.FriendDao;
import com.project.socialnetwork.dao.UserDao;
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

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    public FriendService(FriendDao friendDao, UserDao userDao) {
        this.friendDao = friendDao;
        this.userDao = userDao;
    }

    public List<FriendsList> getAllFriends(Long userId) {
        User user = userDao.findById(userId).orElse(null);
        if (user != null) {
            return user.getFriends();
        }
        return List.of();
    }

    public void unFriend(Long userId, Long friendId) {
        if (isFriend(userId, friendId)) {
            friendDao.deleteBySenderIDUserIdAndFriendUserId(userId, friendId);
            logger.info("User with ID {} unfriended user with ID {}", userId, friendId);
        } else {
            logger.warn("User with ID {} attempted to unfriend user with ID {}, but they are not friends", userId, friendId);
        }
    }

    public ResponseEntity<String> sendFriendRequest(Long userId, Long friendId) {
        Optional<User> userOptional = userDao.findById(userId);
        Optional<User> friendOptional = userDao.findById(friendId);

        if (userOptional.isEmpty() || friendOptional.isEmpty()) {
            logger.error("User or friend is empty while sending friend request");
            return ResponseEntity.badRequest().body(ResponseUtils.USER_EMPTY);
        }

        User user = userOptional.get();
        User friend = friendOptional.get();

        if (areFriends(user, friend)) {
            return ResponseEntity.badRequest().body(ResponseUtils.ALREADY_FRIENDS);
        }

        FriendsList friendship = new FriendsList();
        friendship.setFriend(friend);
        friendship.setSenderID(user);
        friendship.setRequestStatus(RequestStaus.PENDING);
        friendship.setDate(LocalDate.now());

        friendDao.save(friendship);

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }

    private boolean areFriends(User user, User friend) {
        return friendDao.existsBySenderIDUserIdAndFriendUserId(user, friend)
                || friendDao.existsBySenderIDUserIdAndFriendUserId(friend, user);
    }

    private boolean isFriend(Long userId, Long friendId) {
        return friendDao.findBySenderIDUserIdAndFriendUserId(userId, friendId);
    }

    public ResponseEntity<String> acceptFriendRequest(Long userId, Long friendId) {
        FriendsList friendship = friendDao.findBySenderIDUser_IdAndFriendUser_Id(friendId, userId);

        if (friendship == null) {
            return ResponseEntity.badRequest().body(ResponseUtils.FRIEND_REQUEST_NOT_FOUND);
        }

        friendship.setRequestStatus(RequestStaus.ACCEPTED);
        friendship.setDate(LocalDate.now());

        friendDao.save(friendship);

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_ACCEPTED);
    }
}
