package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends JpaRepository<FriendsList, Long> {

    void deleteBySenderIDUserIdAndFriendUserId(Long userId, Long friendsId);

    boolean existsBySenderIDUserIdAndFriendUserId(User user, User friend);

//    FriendsList findBySenderIDUser_IdAndFriendUser_Id(Long userId, Long friendId);


    boolean findBySenderIDUserIdAndFriendUserId(Long userId, Long friendId);

    FriendsList findBySenderIDUser_IdAndFriendUser_Id(Long userId, Long friendId);
}