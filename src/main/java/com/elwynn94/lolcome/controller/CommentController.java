package com.elwynn94.lolcome.controller;

import com.elwynn94.lolcome.dto.HttpResponseDto;
import com.elwynn94.lolcome.dto.comment.CommentCreateRequestDto;
import com.elwynn94.lolcome.dto.comment.CommentResponseDto;
import com.elwynn94.lolcome.dto.comment.CommentUpdateRequestDto;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.security.UserDetailsImpl;
import com.elwynn94.lolcome.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<HttpResponseDto> createComment(@PathVariable Long postId,
                                                         @RequestBody CommentCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        commentService.createComment(postId, requestDto, user);

        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글이 성공적으로 작성되었습니다.")
                        .build()
        );
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<HttpResponseDto> readComments(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.readComments(postId);

        if (!comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    HttpResponseDto.builder()
                            .message("댓글이 성공적으로 조회되었습니다.")
                            .data(comments)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    HttpResponseDto.builder()
                            .message("가장 먼저 댓글을 남겨보세요.")
                            .build()
            );
        }
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<HttpResponseDto> updateComment(@PathVariable Long commentId,
                                                         @RequestBody CommentUpdateRequestDto requestDto) {
        commentService.updateComment(commentId, requestDto, null);

        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글이 성공적으로 수정되었습니다.")
                        .build()
        );
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<HttpResponseDto> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId, null);

        return ResponseEntity.status(HttpStatus.OK).body(
                HttpResponseDto.builder()
                        .message("댓글이 성공적으로 삭제되었습니다.")
                        .build()
        );
    }
}