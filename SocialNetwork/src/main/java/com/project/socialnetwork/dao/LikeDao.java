package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.Likes;
import com.project.socialnetwork.entity.Post;
import com.project.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDao extends JpaRepository<Likes,Long> {

    void deleteByPostAndUser(Post postId, User userId);
}
