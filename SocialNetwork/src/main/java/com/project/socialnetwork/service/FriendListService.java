package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.FriendRequestRepo;
import com.project.socialnetwork.dao.FriendsListRepo;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.FriendRequest;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.RequestStatus;
import com.project.socialnetwork.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FriendListService {

    @Autowired
    private FriendsListRepo friendsListRepo;

    @Autowired
    private FriendRequestRepo friendRequestRepo;

    @Autowired
    private UserDao userDao;

    public List<FriendsList> showAllFriends() {
        return friendsListRepo.findAll();
    }

    public void deleteFriend(Long friendId) {
        friendsListRepo.deleteById(friendId);
    }

    public void sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userDao.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + senderId));
        User receiver = userDao.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + receiverId));

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same user.");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(RequestStatus.PENDING);
        friendRequestRepo.save(friendRequest);
    }

    public void processFriendRequest(Long requestId, boolean accept) {
        FriendRequest friendRequest = friendRequestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found."));

        if (accept) {
            FriendsList friendsList = new FriendsList();
            friendsList.setUser(friendRequest.getSender());
            friendsList.setFriend(friendRequest.getReceiver());
            friendsList.setStatus(RequestStatus.ACCEPTED);
            friendsList.setFriendsSince(LocalDate.now());
            friendsListRepo.save(friendsList);
        }

        friendRequestRepo.delete(friendRequest);
    }

    public User searchUser(Long userId) {
        Optional<User> userOptional = userDao.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
}
