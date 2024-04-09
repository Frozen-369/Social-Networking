package com.project.socialnetwork.controller;

import com.project.socialnetwork.dao.LikeDao;
import com.project.socialnetwork.dao.PostDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.entity.UserProfile;
import com.project.socialnetwork.service.FriendService;
import com.project.socialnetwork.service.LikeService;
import com.project.socialnetwork.service.UserService;
import com.project.socialnetwork.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final FriendService friendsService;
    private final UserDao userDao;

    private final LikeService likeService;
    private final PostDao postDao;

    @Autowired
    public UserController(UserService userService, FriendService friendsService, UserDao userDao, LikeService likeService, LikeDao likeDao, PostDao postDao) {
        this.userService = userService;
        this.friendsService = friendsService;
        this.userDao = userDao;
        this.likeService = likeService;
        this.postDao = postDao;
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

    @GetMapping("/{user_id}/allfriends")
    public ResponseEntity<List<FriendsList>> seeFriendGroup(@PathVariable User user) {
        List<FriendsList> friendsList = friendsService.getAllFriends(user);
        return ResponseEntity.ok().body(friendsList);
    }

    @DeleteMapping("/{user_id}/allfriends/{friends_id}")
    public ResponseEntity<String> unFriend(@PathVariable Long user_id, @PathVariable Long friends_id) {
        Optional<User> user = userDao.findById(user_id);
        Optional<User> friend = userDao.findById(friends_id);
        if (user.isEmpty() || friend.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_OR_FRIEND_NOT_FOUND);
        }
        friendsService.unFriend(user.orElse(null), friend.orElse(null));
        return ResponseEntity.ok().body(ResponseUtils.USER_UNFRIEND);
    }

    @PostMapping("/{user_id}/sendRequest/{friends_id}")
    public ResponseEntity<String> sendRequest(@PathVariable Long user_id, @PathVariable Long friends_id){
        Optional<User> user = userDao.findById(user_id);
        Optional<User> friend = userDao.findById(friends_id);
        if (user.isEmpty() || friend.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_OR_FRIEND_NOT_FOUND);
        }
        friendsService.sendFriendRequest(user.orElse(null), friend.orElse(null));
        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }


    @PostMapping("/{user_id}/acceptFriendRequest/{friends_id}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long user_id, @PathVariable Long friends_id){
        Optional<User> user1 = userDao.findById(user_id);
        Optional<User> friend = userDao.findById(friends_id);
        if (user1.isEmpty() || friend.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_OR_FRIEND_NOT_FOUND);
        }

        friendsService.acceptFriendRequest(user1.orElse(null), friend.orElse(null));

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_ACCEPTED);
    }

    @PostMapping("/post/{post_id}/like/{user_id}")
    public ResponseEntity<String> likePost(@PathVariable Long post_id, @PathVariable Long user_id){
        Optional<Post> postID = postDao.findById(post_id);
        Optional<User> user1 = userDao.findById(user_id);
        if (postID.isEmpty() && user1.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_AND_POST_NOT_FOUND);
        } else if (postID.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.POST_NOT_CREATED);
        } else if (user1.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_EMPTY);
        }

        Post post_ID = postID.get();
        User user_ID = user1.get();

        likeService.likePost(post_ID, user_ID);

        return ResponseEntity.ok().body(ResponseUtils.POST_LIKED);
    }

    @DeleteMapping("/post/{post_id}/unlike/{user_id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long post_id, @PathVariable Long user_id){
        Optional<Post> postID = postDao.findById(post_id);
        Optional<User> user = userDao.findById(user_id);
        if (postID.isEmpty() && user.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_AND_POST_NOT_FOUND);
        } else if (postID.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.POST_NOT_CREATED);
        } else if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_EMPTY);
        }

        Post post = postID.get();
        User user_ID = user.get();
        likeService.unlikePost(post, user_ID);
        return ResponseEntity.ok().body(ResponseUtils.POST_UNLIKED);
    }
}


