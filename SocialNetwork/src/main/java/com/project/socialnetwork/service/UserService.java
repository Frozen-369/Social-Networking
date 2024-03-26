package com.project.socialnetwork.service;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.dao.UserProfileDao;
import com.project.socialnetwork.entity.User;
import com.project.socialnetwork.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;


    public User registerUser(User user){
        return userDao.save(user);
    }

    public UserProfile updateProfile(UserProfile userProfile, Long user_id){
        User user = userDao.findById(user_id).get();
        userProfile.setUser(user);
        return userProfileDao.save(userProfile);
    }


}
