package com.project.socialnetwork.service;
import com.project.socialnetwork.dao.UserDao;
import com.project.socialnetwork.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User registerNewUser(User user){
        return userDao.save(user);
    }


}
