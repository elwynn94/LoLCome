package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.EmailVerification;
import com.elwynn94.lolcome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByCode(String code);
    Optional<EmailVerification> findByUser(User user);
}

