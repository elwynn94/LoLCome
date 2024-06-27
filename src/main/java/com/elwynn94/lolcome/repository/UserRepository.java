package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.entity.UserRole;
import com.elwynn94.lolcome.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndStatus(String userId, UserStatusEnum status);
}