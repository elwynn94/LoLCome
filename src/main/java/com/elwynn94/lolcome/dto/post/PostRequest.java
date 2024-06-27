package com.elwynn94.lolcome.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    @NotBlank
    private String summonerName;

    @NotNull
    private String content;

    @NotNull
    private String mainPosition;

    @NotNull
    private String tier;

    @NotNull
    private String hopeChampion;

    @NotNull
    private String winRate;

    @NotNull
    private String kda;
}
