package com.elwynn94.lolcome.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Setter
@Getter
@Value
public class LoginRequestDto {
    private String userId;
    private String password;
}