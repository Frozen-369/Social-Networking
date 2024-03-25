package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerNewUser")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerNewUser(user);
        return ResponseEntity.ok(savedUser);
    }

}
