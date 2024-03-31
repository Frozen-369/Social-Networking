package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.FriendRequest;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.service.FriendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendlist")
public class FriendsListController {
    @Autowired
    private FriendListService friendListService;

    @GetMapping("/friends")
    public ResponseEntity<List<FriendsList>> showFriendList() {
        List<FriendsList> friendsLists = friendListService.showAllFriends();
        return ResponseEntity.ok(friendsLists);
    }

    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long friendId) {
        friendListService.deleteFriend(friendId);
        return ResponseEntity.ok("Friend deleted successfully");
    }

    @PutMapping("/requests/{requestId}")
    public ResponseEntity<String> updateRequestStatus(@PathVariable Long requestId, @RequestParam boolean accept) {
        friendListService.processFriendRequest(requestId, accept);
        return ResponseEntity.ok("Friend request status updated successfully");
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = friendListService.searchUser(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/requests")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
        friendListService.sendFriendRequest(friendRequest);
        return ResponseEntity.ok("Friend request sent successfully");
    }
    @PostMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestBody FriendsList friendsList,@RequestParam Long senderId, @RequestParam Long receiverId) {
        friendListService.addFriend(senderId, receiverId);
        return ResponseEntity.ok("Friend added successfully.");
    }

}


