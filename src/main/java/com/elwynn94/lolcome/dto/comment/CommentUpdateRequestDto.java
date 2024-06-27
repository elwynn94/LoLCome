package com.elwynn94.lolcome.dto.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    @NotBlank(message = "수정할 내용을 입력해주세요! ")
    private String content;

    @JsonCreator
    public  CommentUpdateRequestDto(String content){
        this.content = content;
    }
}