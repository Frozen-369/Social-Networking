package com.project.socialnetwork.dao;

import com.project.socialnetwork.entity.Comments;
import com.project.socialnetwork.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentsDao extends JpaRepository<Comments, Long>,PagingAndSortingRepository<Comments,Long> {

    Long countByCommentId(Comments comments);
//    List<Comments> findByPostOrderByCreatedAtAsc(Post post);
    boolean existsByCommentId(Long commentsID);


    Page<Comments> findByPost(Post post, Pageable pageable);

    Optional<Comments> findByCommentId(Comments comments);

    Optional<Comments> findById(Long commentId);

    void deleteByPostAndCommentId(Post post,Comments commentId);
}
