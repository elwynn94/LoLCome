package com.elwynn94.lolcome.controller;

import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.security.UserDetailsImpl;
import com.elwynn94.lolcome.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{postId}/comment/{commentId}/like")
    public HttpStatusResponseDto doLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.doLike(postId, commentId, userDetails.getUser().getId());
    }

    @DeleteMapping("/{postId}/comment/{commentId}/like")
    public HttpStatusResponseDto undoLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.undoLike(postId, commentId, userDetails.getUser().getId());
    }
}
