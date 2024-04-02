package com.project.socialnetwork.controller;

import com.project.socialnetwork.entity.*;
import com.project.socialnetwork.service.FriendService;
import com.project.socialnetwork.service.LikeService;
import com.project.socialnetwork.service.UserService;
import com.project.socialnetwork.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final FriendService friendsService;

    private final LikeService likeService;

    @Autowired
    public UserController(UserService userService, FriendService friendsService, LikeService likeService) {
        this.userService = userService;
        this.friendsService = friendsService;
        this.likeService = likeService;
    }

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

    @GetMapping("{user_id}/allfriends")
    public ResponseEntity<List<FriendsList>> seeFriendGroup(@PathVariable Long userId) {
        List<FriendsList> friendsList = friendsService.getAllFriends(userId);
        return ResponseEntity.ok().body(friendsList);
    }

    @DeleteMapping("{user_id}/allfriends/{friends_id}")
    public ResponseEntity<String> unFriend(@PathVariable Long userId, @PathVariable Long friendsId) {
        friendsService.unFriend(userId, friendsId);
        return ResponseEntity.ok().body(ResponseUtils.USER_UNFRIEND);
    }

    @PostMapping("{user_id}/sendRequest/{friends_id}")
    public ResponseEntity<String> sendRequest(@PathVariable Long userId, @PathVariable Long friendsId){
        friendsService.sendFriendRequest(userId, friendsId);
        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }

    @PostMapping("/post/{post_id}/like/{user_id}")
    public ResponseEntity<String> likePost(@PathVariable Long post_id, @PathVariable Long user_id){
        likeService.likePost(post_id, user_id);
        return ResponseEntity.ok().body(ResponseUtils.POST_LIKED);
    }

    @DeleteMapping("/post/{post_id}/unlike/{user_id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long post_id, @PathVariable Long user_id){
        likeService.unlikePost(post_id, user_id);
        return ResponseEntity.ok().body(ResponseUtils.POST_UNLIKED);
    }


}


