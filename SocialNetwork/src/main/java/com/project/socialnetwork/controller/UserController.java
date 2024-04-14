package com.project.socialnetwork.controller;

import com.project.socialnetwork.dao.CommentsDao;
import com.project.socialnetwork.dao.LikeDao;
import com.project.socialnetwork.dao.PostDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.*;
import com.project.socialnetwork.service.CommentService;
import com.project.socialnetwork.service.FriendService;
import com.project.socialnetwork.service.LikeService;
import com.project.socialnetwork.service.UserService;
import com.project.socialnetwork.utils.ResponseUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final CommentsDao commentsDao;
    private final CommentService commentService;

    @Autowired
    public UserController(UserService userService, FriendService friendsService, UserDao userDao, LikeService likeService, LikeDao likeDao, PostDao postDao, CommentsDao commentsDao, CommentService commentService) {
        this.userService = userService;
        this.friendsService = friendsService;
        this.userDao = userDao;
        this.likeService = likeService;
        this.postDao = postDao;
        this.commentsDao = commentsDao;
        this.commentService = commentService;
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
    public ResponseEntity<String> sendRequest(@PathVariable Long user_id, @PathVariable Long friends_id,@RequestBody CustomFriends customFriends,@RequestBody FriendsList friendsList){
        Optional<User> user = userDao.findById(user_id);
        Optional<User> friend = userDao.findById(friends_id);
        if (user.isEmpty() || friend.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_OR_FRIEND_NOT_FOUND);
        }
        friendsService.sendFriendRequest(user.orElse(null), friend.orElse(null));
        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_SENT);
    }


    @PostMapping("/{user_id}/acceptFriendRequest/{friends_id}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long user_id, @PathVariable Long friends_id ,@RequestBody CustomFriends customFriends,@RequestBody FriendsList friendsList){
        Optional<User> user1 = userDao.findById(user_id);
        Optional<User> friend = userDao.findById(friends_id);
        if (user1.isEmpty() || friend.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.USER_OR_FRIEND_NOT_FOUND);
        }

        friendsService.acceptFriendRequest(user1.orElse(null), friend.orElse(null));

        return ResponseEntity.ok().body(ResponseUtils.FRIEND_REQUEST_ACCEPTED);
    }

    @PostMapping("/post/{post_id}/like/{user_id}")
    public ResponseEntity<String> likePost(@PathVariable Long post_id, @PathVariable Long user_id ,@RequestBody Likes likes){
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

        Long totalLikes = likeService.countPostLikes(post_ID);

        return ResponseEntity.ok().body("Post liked. Total likes: " + totalLikes);
    }

    @DeleteMapping("/post/{post_id}/unlike/{user_id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long post_id, @PathVariable Long user_id,@RequestBody Likes likes){
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

        Long totalLikes = likeService.countPostLikes(post);

        return ResponseEntity.ok().body("Post unliked. Total likes: " + totalLikes);
    }

    @PostMapping("/comments")
    public ResponseEntity<String> writeComments(@RequestBody Comments comments){
        commentService.writeComments(comments);
        return ResponseEntity.ok().body(ResponseUtils.COMMENTSADDED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<Comments>> viewCommentsByPostId(
            @PathVariable("postId") Long postId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Comments> commentsPage = commentService.viewAllComments(postId, page, size);
        Long totalComments= commentService.getTotalCount(commentsPage);
        return new ResponseEntity<>(commentsPage, HttpStatus.OK);
    }

    @DeleteMapping("{postID}/deleteComments/{commentID}")
    public ResponseEntity<String> deleteCommentsById(@PathVariable Long postID,@PathVariable Long commentID) {
        Optional<Post> checkPostID = postDao.findById(postID);
        if (checkPostID.isPresent()) {
            Post post_ID = checkPostID.get();
            Optional<Comments> checkCommentsID = commentsDao.findById(commentID);
            if (checkCommentsID.isPresent()) {
                Comments comments_Id = checkCommentsID.get();
                commentService.deleteComments(post_ID, comments_Id);
                return  ResponseEntity.ok().body(ResponseUtils.COMMENTS_DELETED);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid commentsID.");
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid postId.");
        }
    }

    @PostMapping("{postID}/editComments/")
    public ResponseEntity<String> editComments(@PathVariable Long postID,@RequestBody Comments editedcomments){
        Optional<Post> findPost = postDao.findById(postID);

        if (findPost.isPresent()) {
            Long commentId = editedcomments.getCommentId();
            Optional<Comments> storeComments = commentsDao.findById(commentId);

            if (storeComments.isPresent()) {
                commentService.editComments(editedcomments);
                return ResponseEntity.ok(ResponseUtils.COMMENT_HAS_BEEN_EDITED);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid commentsID.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid postId.");
        }
    }
}


