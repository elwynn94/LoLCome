package com.elwynn94.lolcome.dto.post;

import com.elwynn94.lolcome.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long postId;
    private final String summonerName;
    private final String content;
    private final String mainPosition;
    private final String tier;
    private final String hopeChampion;
    private final String winRate;
    private final String kda;

//    public PostResponse(Post post) {
//        this.postId = post.getPostId();
//        this.title = post.getTitle();
//        this.content = post.getContent();
//    }


    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.summonerName = post.getSummonerName();
        this.content = post.getContent();
        this.mainPosition = post.getMainPosition();
        this.tier = post.getTier();
        this.hopeChampion = getHopeChampion();
        this.winRate = getWinRate();
        this.kda = getKda();
    }
}