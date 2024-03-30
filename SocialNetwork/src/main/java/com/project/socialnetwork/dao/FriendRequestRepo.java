package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.FriendRequest;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepo extends JpaRepository<FriendRequest,Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

}
