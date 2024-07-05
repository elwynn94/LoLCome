package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentLikeRepositoryCustom {
    Page<Comment> findLikedCommentsByUserId(Long userId, Pageable pageable);
}