package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.entity.UserProfile;
import com.project.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/updateProfile/{user_id}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable("user_id") Long user_id, @RequestBody UserProfile userProfile){
        UserProfile updatedProfile = userService.updateProfile(userProfile, user_id);
        return ResponseEntity.ok(updatedProfile);
    }

}
