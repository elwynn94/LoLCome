package com.elwynn94.lolcome.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 50)
    private String summonerName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    private String mainPosition;

    private String tier;

    private String hopeChampion;

    private String winRate;

    private String kda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(Long postId, String summonerName, String content, String mainPosition, String tier,
                String hopeChampion, String winRate, String kda, User user) {
        this.postId = postId;
        this.summonerName = summonerName;
        this.content = content;
        this.mainPosition = mainPosition;
        this.tier = tier;
        this.hopeChampion = hopeChampion;
        this.winRate = winRate;
        this.kda = kda;
        this.user = user;
    }
}