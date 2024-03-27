package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Long> {
}
