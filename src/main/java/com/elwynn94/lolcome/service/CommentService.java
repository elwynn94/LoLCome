package com.elwynn94.lolcome.service;

import com.elwynn94.lolcome.dto.comment.CommentCreateRequestDto;
import com.elwynn94.lolcome.dto.comment.CommentResponseDto;
import com.elwynn94.lolcome.dto.comment.CommentUpdateRequestDto;
import com.elwynn94.lolcome.entity.Comment;
import com.elwynn94.lolcome.entity.Post;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.repository.CommentRepository;
import com.elwynn94.lolcome.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void createComment(Long postId, CommentCreateRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        Comment comment = new Comment(requestDto.getContent(), post, user);
        commentRepository.save(comment);
    }

    public List<CommentResponseDto> readComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostPostId(postId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public void updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        if (user != null && !comment.getUser().equals(user)) {
            throw new AccessDeniedException("Unauthorized user");
        }
        commentRepository.delete(comment);
    }
}