package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostPostId(Long postId);
    Page<Comment> findLikedCommentsByUserId(Long userId, Pageable pageable);
}