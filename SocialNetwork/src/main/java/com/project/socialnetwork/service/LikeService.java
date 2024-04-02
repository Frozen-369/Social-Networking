package com.project.socialnetwork.service;

import com.project.socialnetwork.dao.LikeDao;
import com.project.socialnetwork.dao.PostDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.Like;
import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeDao likeDao;
    private final PostDao postDao;
    private final UserDao userDao;

    @Autowired
    public LikeService(LikeDao likeDao, PostDao postDao, UserDao userDao) {
        this.likeDao = likeDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    public void likePost(Long postId, Long userId) {
        Post post = postDao.findById(postId).orElse(null);
        User user = userDao.findById(userId).orElse(null);

        if (post != null && user != null) {
            Like like = new Like();
            like.setPost(post);
            like.setUserId(user.getUser_id());
            likeDao.save(like);
        }
    }

    public void unlikePost(Long postId, Long userId) {
        likeDao.deleteByPostIdAndUserId(postId, userId);
    }
}
