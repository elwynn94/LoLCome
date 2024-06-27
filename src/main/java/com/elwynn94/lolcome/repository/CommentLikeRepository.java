package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.CommentLike;
import com.elwynn94.lolcome.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {
    Optional<CommentLike> findByIdUserIdAndIdCommentId(Long userId, Long commentId);
    boolean existsByIdUserIdAndIdCommentId(Long userId, Long commentId);
}