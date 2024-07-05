package com.elwynn94.lolcome.service;

import com.elwynn94.lolcome.config.ResponseCode;
import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.entity.Comment;
import com.elwynn94.lolcome.entity.CommentLike;
import com.elwynn94.lolcome.entity.CommentLikeId;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.repository.CommentLikeRepository;
import com.elwynn94.lolcome.repository.CommentRepository;
import com.elwynn94.lolcome.repository.PostRepository;
import com.elwynn94.lolcome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 좋아요 등록
    public HttpStatusResponseDto doLike(Long postId, Long commentId, Long userId) {
        if (checkInputs(postId, commentId, userId)) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }

        // 예외 1) 이미 해당 userId-commentId 로 좋아요 등록되어있는 경우
        if (commentLikeRepository.existsByIdUserIdAndIdCommentId(userId, commentId)) {
            return new HttpStatusResponseDto(ResponseCode.DUPLICATE_ENTITY);
        }

        // 예외 2) 자신의 댓글에 좋아요한 경우
        Comment comment = commentRepository.findById(commentId).orElse(null);
        assert comment != null;
        if (userId.equals(comment.getUser().getId())) {
            return new HttpStatusResponseDto(ResponseCode.DO_NOT_LIKE_MY_COMMENT);
        }

        User user = userRepository.findById(userId).orElse(null);
        commentLikeRepository.save(new CommentLike(new CommentLikeId(userId, commentId), user, comment));
        return new HttpStatusResponseDto(ResponseCode.SUCCESS);
    }

    // 댓글 좋아요 취소 (삭제)
    public HttpStatusResponseDto undoLike(Long postId, Long commentId, Long userId) {
        if (checkInputs(postId, commentId, userId)) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }

        // 사용자 요청 userId-commentId가 유효한지 검색
        CommentLike commentLike = commentLikeRepository.findByIdUserIdAndIdCommentId(userId, commentId).orElse(null);
        if (commentLike == null) {
            return new HttpStatusResponseDto(ResponseCode.INVALID_INPUT_VALUE);
        }

        commentLikeRepository.delete(commentLike);
        return new HttpStatusResponseDto(ResponseCode.SUCCESS);
    }


    // 좋아요한 댓글 조회
    public Page<Comment> getLikedComments(Long userId, Pageable pageable) {
        return commentLikeRepository.findLikedCommentsByUserId(userId, pageable);
    }

    private boolean checkInputs(Long postId, Long commentId, Long userId) {
        // postId, commentId, userId가 유효한지 확인 (등록되어있는 postId, commentId, userId인지 확인)
        if (postRepository.existsById(postId) && commentRepository.existsById(commentId) && userRepository.existsById(userId)) {
            return false;
        }
        return true;
    }
}