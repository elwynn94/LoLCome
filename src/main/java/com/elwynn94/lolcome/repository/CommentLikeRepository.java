package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Comment;
import com.elwynn94.lolcome.entity.CommentLike;
import com.elwynn94.lolcome.entity.CommentLikeId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId>, CommentLikeRepositoryCustom {
    Optional<CommentLike> findByIdUserIdAndIdCommentId(Long userId, Long commentId);
    boolean existsByIdUserIdAndIdCommentId(Long userId, Long commentId);
}