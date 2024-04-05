package com.project.socialnetwork.dao;


import com.project.socialnetwork.entity.CustomFriends;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFriendDao extends JpaRepository<CustomFriends, Long> {

    boolean existsBySenderAndFriend(User sender, User friend);

    boolean existsByFriendAndSender(User friend, User sender);

    CustomFriends findBySenderAndFriend(User sender, User friend);

    void deletBySenderAndFriend(User user, User friend);
}