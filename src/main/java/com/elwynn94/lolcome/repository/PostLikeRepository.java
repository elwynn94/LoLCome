package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.PostLike;
import com.elwynn94.lolcome.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    Optional<PostLike> findByIdUserIdAndIdPostId(Long userId, Long postId);
    boolean existsByIdUserIdAndIdPostId(Long userId, Long postId);
}
