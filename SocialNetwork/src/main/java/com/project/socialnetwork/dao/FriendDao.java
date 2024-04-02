package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendDao extends JpaRepository<FriendsList, Long> {

    void deleteBySenderIDUser_idAndFriendUseruser_id(Long userId, Long friendsId) ;

    boolean existsBySenderIDUser_idAndFriendUseruser_id(User user, User friend);

    FriendsList findBySenderIDAndFriend(Long friendId, Long userId);

    boolean findBySenderIDUser_idAndFriendUseruser_id(Long userId, Long friendId);
}