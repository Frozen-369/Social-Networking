package com.project.socialnetwork.service;
import com.project.socialnetwork.Exception.NotFoundException;
import com.project.socialnetwork.dao.PostDao;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.dao.UserProfileDao;
import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Autowired
    private PostDao postDao;

    public User registerUser(User user){
        return userDao.save(user);
    }

    public UserProfile updateProfile(UserProfile userProfile, Long userId){
        User user = userDao.findById(userId).get();
        userProfile.setUser(user);
        return userProfileDao.save(userProfile);
    }


    public Post postDetails(Post postDetails, Long user_id) {
        User user = userDao.findById(user_id).get();
        postDetails.setUser(user);
        return postDao.save(postDetails);
    }
}
