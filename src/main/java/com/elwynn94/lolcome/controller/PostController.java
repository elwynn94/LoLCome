package com.elwynn94.lolcome.controller;

import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.dto.post.PostRequest;
import com.elwynn94.lolcome.entity.Post;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.jwt.JwtUtil;
import com.elwynn94.lolcome.security.UserDetailsImpl;
import com.elwynn94.lolcome.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @PostMapping("/posts")
    public HttpStatusResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody Post post) {
        // 현재 인증된 사용자를 가져와 게시글의 작성자로 설정
        User author = userDetails.getUser();
        post.setUser(author);
        // 게시글 저장
        return postService.createPost(post);
    }

    @GetMapping("/posts")
    public HttpStatusResponseDto getAllPosts(
            @RequestParam int page,
            @RequestParam int size) {
        return postService.getAllPosts(page, size);
    }

    @GetMapping("/{userId}/following/posts")
    public HttpStatusResponseDto getPostsOfFollowees(@PathVariable String userId,
                                                     HttpServletRequest request) {
        String token = jwtUtil.getAccessTokenFromHeader(request);
        return postService.getPostsOfFollowees(token, userId);
    }

    @GetMapping("/{userId}/posts")
    public HttpStatusResponseDto getPostsByUserId(@PathVariable String userId, @RequestParam int page, @RequestParam int size) {
        return postService.getPostsByUserId(userId, page, size);
    }

    @GetMapping("/{userId}/posts/{postId}")
    public HttpStatusResponseDto getPostById(@PathVariable String userId,
                                             @PathVariable Long postId) {
        return postService.getPostById(userId, postId);
    }

    @PutMapping("/posts/{postId}")
    public HttpStatusResponseDto updatePost(@PathVariable Long postId,
                                            @RequestBody PostRequest postRequest,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        return postService.updatePost(user, postId, postRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public HttpStatusResponseDto deletePost(@PathVariable Long postId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.deletePost(user, postId);
    }
}