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



    public User getUserById(Long id){
        User user = userDao.findById(id).orElse(null);
        if(user == null) {
            throw new NullPointerException("User is empty");
        }
        return user;
    }
    public UserProfile createProfile(UserProfile userProfile, Long userId){
        Optional<User> userOptional = Optional.ofNullable(getUserById(userId));
        if(userOptional.isPresent()){
            User user = userOptional.get();
            userProfile.setUser(user);
            return userProfileDao.save(userProfile);
        }
        else{
            throw new NotFoundException("User not found");
        }

    }

    public UserProfile getUserProfileById(Long id){
        UserProfile userProfile = userProfileDao.findById(id).orElse(null);
        if(userProfile == null) {
            throw new NullPointerException("User is empty");
        }
        return userProfile;
    }

    public void updateProfile(UserProfile userProfile) throws Exception {
        Optional<UserProfile> userProfileOptional = Optional.ofNullable(getUserProfileById(userProfile.getProfile_id()));
        if(userProfileOptional.isPresent()){

            UserProfile userProfile1 = userProfileOptional.get();

            userProfile1.setFirstname(userProfile.getFirstname() != null ? userProfile.getFirstname(): userProfile1.getFirstname());
            userProfile1.setLastname(userProfile.getLastname() != null ? userProfile.getLastname(): userProfile1.getLastname());
            userProfile1.setBio(userProfile.getAddress() != null ? userProfile.getAddress(): userProfile1.getAddress());
            userProfile1.setProfile_pic_url(userProfile.getProfile_pic_url() != null ? userProfile.getProfile_pic_url(): userProfile1.getProfile_pic_url());
            userProfile1.setBirthday(userProfile.getBirthday() != null ? userProfile.getBirthday(): userProfile1.getBirthday());

            userProfileDao.save(userProfile1);

        }
        else {
            throw new NotFoundException("User not found.");
        }
    }


    public Post postDetails(Post postDetails, Long user_id) {
        User user = userDao.findById(user_id).get();
        postDetails.setUser(user);
        return postDao.save(postDetails);
    }

    public Post getPostById(Long postId) {
        Post post = postDao.findById(postId).orElse(null);
        if(post == null) {
            throw new NullPointerException("User is empty");
        }
        return post;
    }

    public void deletePost(Long postId) {
        Post post = getPostById(postId);
        if(post == null){
            throw new NullPointerException("Id cannot be empty");
        }
        postDao.delete(post);
    }

}
