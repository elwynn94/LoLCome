package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostLikeRepositoryCustom {
    Page<Post> findLikedPostsByUserId(Long userId, Pageable pageable);
}
