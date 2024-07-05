package com.elwynn94.lolcome.service;

import com.elwynn94.lolcome.config.ResponseCode;
import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.dto.post.PostRequest;
import com.elwynn94.lolcome.dto.post.PostResponse;
import com.elwynn94.lolcome.entity.Follow;
import com.elwynn94.lolcome.entity.PaginationResponse;
import com.elwynn94.lolcome.entity.Post;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.jwt.JwtUtil;
import com.elwynn94.lolcome.repository.PostRepository;
import com.elwynn94.lolcome.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public HttpStatusResponseDto createPost(Post post) {
        postRepository.save(post);
        return new HttpStatusResponseDto(ResponseCode.CREATED, new PostResponse(post));
    }

    @Transactional(readOnly = true)
    @NonNull
    public HttpStatusResponseDto getAllPosts(int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAll(pageable);
        List<PostResponse> posts = postsPage.stream().map(PostResponse::new).toList();

        PaginationResponse paginationResponse = new PaginationResponse(
                posts,
                postsPage.getNumber(),
                postsPage.getSize(),
                postsPage.getTotalElements(),
                postsPage.getTotalPages(),
                postsPage.isLast()
        );

        return new HttpStatusResponseDto(ResponseCode.SUCCESS, paginationResponse);
    }

    @Transactional(readOnly = true)
    public HttpStatusResponseDto getPostById(String userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty() || !optionalPost.get().getUser().getUserId().equals(userId)) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
        Post post = optionalPost.get();
        return new HttpStatusResponseDto(ResponseCode.SUCCESS, new PostResponse(post));
    }

    @Transactional(readOnly = true)
    public HttpStatusResponseDto getPostsByUserId(String userId, int page, int size) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }

        User user = optionalUser.get();
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postsPage = postRepository.findByUser(user, pageable);
        List<PostResponse> posts = postsPage.stream().map(PostResponse::new).toList();

        PaginationResponse paginationResponse = new PaginationResponse(
                posts,
                postsPage.getNumber(),
                postsPage.getSize(),
                postsPage.getTotalElements(),
                postsPage.getTotalPages(),
                postsPage.isLast()
        );

        return new HttpStatusResponseDto(ResponseCode.SUCCESS, paginationResponse);
    }

    @Transactional(readOnly = true)
    public HttpStatusResponseDto getPostsOfFollowers(String token, String userId) {
        if (!isValidUser(token, userId)) {
            return new HttpStatusResponseDto(ResponseCode.UNAUTHORIZED);
        }

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }

        User user = optionalUser.get();

        List<Follow> followees = user.getFollowing();
        List<PostResponse> postResponses = new ArrayList<>();
        for (var followee : followees) {
            User follwedUser = followee.getFollowed();
            List<Post> posts = postRepository.findByUser(follwedUser);

            for (Post post : posts) {
                postResponses.add(new PostResponse(post));
            }
        }
        return new HttpStatusResponseDto(ResponseCode.SUCCESS, postResponses);
    }

    @Transactional
    public HttpStatusResponseDto updatePost(User user, Long postId, PostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty() || !optionalPost.get().getUser().getUserId()
                .equals(user.getUserId())) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }

        Post post = optionalPost.get();
        post.setSummonerName(request.getSummonerName());
        post.setContent(request.getContent());
        Post updatedPost = postRepository.save(post);
        return new HttpStatusResponseDto(ResponseCode.SUCCESS, new PostResponse(updatedPost));
    }

    @Transactional
    public HttpStatusResponseDto deletePost(User user, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty() || !optionalPost.get().getUser().getUserId()
                .equals(user.getUserId())) {
            return new HttpStatusResponseDto(ResponseCode.ENTITY_NOT_FOUND);
        }
        postRepository.delete(optionalPost.get());
        return new HttpStatusResponseDto(ResponseCode.SUCCESS);
    }

    private boolean isValidUser(String token, String userId) {
        String tokenUserId = getUserIdFromToken(token);
        return tokenUserId != null && tokenUserId.equals(userId);
    }

    private String getUserIdFromToken(String token) {
        try {
            return jwtUtil.getUserIdFromJwt(token);
        } catch (Exception e) {
            return null;
        }
    }
}