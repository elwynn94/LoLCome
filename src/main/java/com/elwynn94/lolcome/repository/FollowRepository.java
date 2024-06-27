package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Follow;
import com.elwynn94.lolcome.entity.FollowId;
import com.elwynn94.lolcome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    boolean existsByFollowerAndFollowed(User follower, User followed);
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
}
