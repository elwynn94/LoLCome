package com.elwynn94.lolcome.dto.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

    @NotBlank(message = "작성할 내용을 입력해주세요! ")
    private final String content;

    @JsonCreator
    public CommentCreateRequestDto(String content) {
        this.content = content;
    }
}