package com.elwynn94.lolcome.dto.user;

import jakarta.validation.constraints.*;

public record SignupRequestDto(
        @NotBlank(message = "사용자 ID는 필수 입력 값입니다.")
        @Size(min = 5, max = 20, message = "사용자 ID는 최소 8글자 이상, 최대 20글자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 대소문자 포함 영문 + 숫자만을 허용합니다.")
        String userId,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 10, message = "비밀번호는 최소 10글자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!*()@#$%^&+=]).+$",
                message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
        String password,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email)

{}