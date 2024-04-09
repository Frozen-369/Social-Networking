package com.project.socialnetwork.dao;



import com.project.socialnetwork.entity.CustomFriends;
import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomFriendDao extends JpaRepository<CustomFriends, Long> {

    List<FriendsList> findBySender(User user);

    CustomFriends findBySenderAndReciverId(User user, User friend);

    boolean existsBySenderAndReciverId(User user, User friend);

    boolean existsByReciverIdAndSender(User friend, User user);
}