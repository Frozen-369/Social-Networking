package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileDao extends JpaRepository<UserProfile, Long> {
}
