package com.elwynn94.lolcome.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}