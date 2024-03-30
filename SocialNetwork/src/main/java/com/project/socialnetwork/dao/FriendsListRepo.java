package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.FriendsList;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsListRepo extends JpaRepository<FriendsList, Long> {

    void deleteByUID(long uid);

    FriendsList findByUID(long uid);

    boolean existsByUID(Long uid);
}
