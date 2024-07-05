package com.elwynn94.lolcome.controller;

import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.entity.Post;
import com.elwynn94.lolcome.security.UserDetailsImpl;
import com.elwynn94.lolcome.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public HttpStatusResponseDto doLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.doLike(postId, userDetails.getUser().getId());
    }

    @DeleteMapping("/{postId}/like")
    public HttpStatusResponseDto undoLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.undoLike(postId, userDetails.getUser().getId());
    }

    @GetMapping("/liked")
    public Page<Post> getLikedPosts(@RequestParam Long userId, Pageable pageable) {
        return postLikeService.getLikedPosts(userId, pageable);
    }
}