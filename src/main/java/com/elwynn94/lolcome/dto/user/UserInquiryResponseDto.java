package com.elwynn94.lolcome.dto.user;

import com.elwynn94.lolcome.entity.CommentLike;
import com.elwynn94.lolcome.entity.PostLike;

import java.util.List;

public record UserInquiryResponseDto(String userId,
                                     String name,
                                     String introduction,
                                     String email,
                                     List<PostLike> postLikes,
                                     List<CommentLike> commentLikes)

{}