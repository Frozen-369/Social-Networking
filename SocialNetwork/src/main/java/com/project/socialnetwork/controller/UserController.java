package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.entity.UserProfile;
import com.project.socialnetwork.service.UserService;
import com.project.socialnetwork.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok().body(ResponseUtils.USER_SUCCESS);
    }

    @GetMapping("/getUser/{user_id}")
    public ResponseEntity<String> getUserById(@PathVariable Long user_id){
        User user = userService.getUserById(user_id);
        return ResponseEntity.ok().body(user.toString());

    }



    @PostMapping("/{user_id}/createProfile")
    public ResponseEntity<String> createProfile(@RequestBody UserProfile userProfile, @PathVariable Long user_id){
        userService.createProfile(userProfile,user_id);
        return ResponseEntity.ok().body(ResponseUtils.USER_PROFILE_CREATE);
    }

    
    @GetMapping("/getUserProfile/{userProfile_id}")
    public ResponseEntity<String> getUserProfileById(@PathVariable Long userProfile_id){
        User user = userService.getUserById(userProfile_id);
        return ResponseEntity.ok().body(user.toString());

    }

    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UserProfile userProfile) throws Exception {

        userService.updateProfile(userProfile);
        return ResponseEntity.ok().body(ResponseUtils.USER_PROFILE_UPDATE);
    }


    @PostMapping("/post/{user_id}")
    public ResponseEntity<String> post(@RequestBody Post postDetails, @PathVariable Long user_id){
        userService.postDetails(postDetails, user_id);
        return ResponseEntity.ok().body(ResponseUtils.POST_CREATE);
    }

    @GetMapping("/getPost/{post_id}")
    public ResponseEntity<String> getPostById(@PathVariable Long post_id){
        Post post = userService.getPostById(post_id);
        return ResponseEntity.ok().body(post.toString());

    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long post_id){
        userService.deletePost(post_id);
        return ResponseEntity.ok().body(ResponseUtils.DELETE);
    }



}


