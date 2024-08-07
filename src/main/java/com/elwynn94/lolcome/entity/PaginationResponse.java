package com.elwynn94.lolcome.entity;

import com.elwynn94.lolcome.dto.post.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse {
    private List<PostResponse> posts;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}