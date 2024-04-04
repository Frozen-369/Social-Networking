package com.project.socialnetwork.dao;


import com.project.socialnetwork.entity.CustomFriends;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFriendDao extends JpaRepository<CustomFriends,Long>{
    boolean existsBysenderAndfriends(User user, User friend);

    boolean existsByfriendsAndsender(User friend, User user);

    CustomFriends existsBysenderAndfriends(Long friendId, Long userId);

    void deletBySenderAndFriend(User userId, User friendId);

    CustomFriends existsBySenderAndFriend(User friend, User user);
}
