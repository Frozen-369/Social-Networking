package com.project.socialnetwork.dao;


import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends JpaRepository<FriendsList, Long> {

    void deleteByFriend(User friend);
}