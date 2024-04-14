package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.CommentsDao;
import com.project.socialnetwork.dao.PostDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.Comments;
import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentsDao commentsDao;
    private final PostDao postDao;
    private final UserDao userDao;

    @Autowired
    public CommentService(CommentsDao commentsDao, PostDao postDao, UserDao userDao) {
        this.commentsDao = commentsDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    public ResponseEntity<String> writeComments(Comments comments){
        Long postId = comments.getPost().getPost_id();
        Long userId = comments.getUser().getUserId();
        String commentText = comments.getCommentText();
        if (postId == null || userId == null || commentText == null || commentText.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.COMMENTSEMPTY.toString());
        }
        Optional<Post> postOptional = postDao.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.ok().body(ResponseUtils.POST_NOT_CREATED);
        }
        Optional<User> userOptional = userDao.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.ok().body(ResponseUtils.USERNOTFOUND.toString());
        }
        commentsDao.save(comments);
        return ResponseEntity.ok("Comment created successfully.");

    }

    public void  editComments(Comments editedcomments){
        Long commentsID = editedcomments.getCommentId();
        Optional<Comments> storeComments = commentsDao.findById(commentsID);
        if (storeComments.isPresent()) {
            Comments oldComments = storeComments.get();
            oldComments.setCommentText(editedcomments.getCommentText());
            commentsDao.save(oldComments);
        }
    }

    public Long getTotalCount(Page<Comments> commentsPage) {
        return commentsPage.getTotalElements();
    }

    public Page<Comments> viewAllComments(Long postId, int page, int size) {

        Optional<Post> optionalPost = postDao.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
            return commentsDao.findByPost(post, pageable);
        } else {

            return Page.empty();
        }
    }


    public void deleteComments(Post postId, Comments commentsId) {
        commentsDao.deleteByPostAndCommentId(postId,commentsId);
    }
}
