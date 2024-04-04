package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeDao extends JpaRepository<Likes,Long> {
    void deleteByPostIdAndUserId(Long postId, Long userId);
}
