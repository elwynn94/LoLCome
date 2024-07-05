package com.elwynn94.lolcome.service;

import com.elwynn94.lolcome.config.ResponseCode;
import com.elwynn94.lolcome.dto.user.SignupRequestDto;
import com.elwynn94.lolcome.dto.user.UpdatePasswordRequestDto;
import com.elwynn94.lolcome.dto.user.UpdateProfileRequestDto;
import com.elwynn94.lolcome.dto.user.UserInquiryResponseDto;
import com.elwynn94.lolcome.entity.User;
import com.elwynn94.lolcome.enums.UserStatusEnum;
import com.elwynn94.lolcome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @Transactional
    public ResponseCode signup(SignupRequestDto requestDto) {
        String userId = requestDto.userId();
        String password = requestDto.password();
        String email = requestDto.email();

        // 중복된 사용자 확인
        if (userRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException(ResponseCode.DUPLICATE_ENTITY.getMessage());
        }

        // 탈퇴한 사용자 확인
        if (userRepository.existsByUserIdAndStatus(userId, UserStatusEnum.DELETED)) {
            throw new IllegalArgumentException(ResponseCode.INVALID_INPUT_VALUE.getMessage());
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 생성 및 저장
        User user = new User();
        user.setUserId(userId);
        user.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        user.setEmail(email);
        user.setStatus(UserStatusEnum.UNVERIFIED); // 초기 상태를 UNVERIFIED로 설정

        userRepository.save(user);

        return ResponseCode.CREATED;
    }

    // 프로필 수정
    @Transactional
    public ResponseCode updateProfile(UpdateProfileRequestDto requestDto, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 이름과 한줄소개 업데이트
        if (requestDto.getName() != null) {
            user.setName(requestDto.getName());
        }
        if (requestDto.getIntroduction() != null) {
            user.setIntroduction(requestDto.getIntroduction());
        }

        userRepository.save(user);
        return ResponseCode.SUCCESS;
    }

    // 비밀 번호 변경
    @Transactional
    public ResponseCode updatePassword(UpdatePasswordRequestDto requestDto, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDto.currentPassword(), user.getPassword())) {
            return ResponseCode.UNAUTHORIZED;
        }

        if (passwordEncoder.matches(requestDto.newPassword(), user.getPassword())) {
            return ResponseCode.INVALID_INPUT_VALUE;
        }

        user.setPassword(passwordEncoder.encode(requestDto.newPassword()));
        userRepository.save(user);

        return ResponseCode.SUCCESS;
    }

    // 유저 조회
    @Transactional(readOnly = true)
    public UserInquiryResponseDto getUserProfile(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserInquiryResponseDto(
                user.getUserId(),
                user.getName(),
                user.getIntroduction(),
                user.getEmail(),
                user.getPostLikes(),
                user.getCommentLikes()
        );
    }

}