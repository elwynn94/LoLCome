package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Post;
import com.elwynn94.lolcome.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @NonNull
    Page<Post> findAll(@NonNull Pageable pageable);
    Page<Post> findByUser(User user, Pageable pageable);
    List<Post> findByUser(User user);
}