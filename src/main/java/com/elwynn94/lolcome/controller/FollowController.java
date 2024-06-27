package com.elwynn94.lolcome.controller;

import com.elwynn94.lolcome.config.ResponseCode;
import com.elwynn94.lolcome.dto.HttpStatusResponseDto;
import com.elwynn94.lolcome.security.UserDetailsImpl;
import com.elwynn94.lolcome.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 팔로우 하기
    @PostMapping("/follow/{followedUserid}")
    public ResponseEntity<HttpStatusResponseDto> followUser(
            @PathVariable String followedUserid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String followerUserid = userDetails.getUser().getUserId();
        ResponseCode responseCode = followService.followUser(followerUserid, followedUserid);
        HttpStatusResponseDto response = new HttpStatusResponseDto(responseCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(responseCode.getStatusCode()));
    }

    // 팔로우 취소
    @DeleteMapping("/unfollow/{followedUserid}")
    public ResponseEntity<HttpStatusResponseDto> unfollowUser(
            @PathVariable String followedUserid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String followerUserid = userDetails.getUser().getUserId();
        ResponseCode responseCode = followService.unfollowUser(followerUserid, followedUserid);
        HttpStatusResponseDto response = new HttpStatusResponseDto(responseCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(responseCode.getStatusCode()));
    }
}
