package com.elwynn94.lolcome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class HttpResponseDto {

    private String message;
    private Object data;

//    public HttpResponseDtoJH(String message) {
//        this.message = message;
//    }
}