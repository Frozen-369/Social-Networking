package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendRequestHandler {

    private final FriendService friendsService;

    @Autowired
    public FriendRequestHandler(FriendService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/{userId}/acceptFriendRequest/{friendId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable User userId, @PathVariable User friendId) {
        return friendsService.acceptFriendRequest(userId, friendId);
    }
}
