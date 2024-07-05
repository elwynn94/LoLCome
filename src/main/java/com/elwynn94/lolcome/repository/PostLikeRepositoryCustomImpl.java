package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.elwynn94.lolcome.entity.QPost.post;
import static com.elwynn94.lolcome.entity.QPostLike.postLike;

@RequiredArgsConstructor
@Repository
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> findLikedPostsByUserId(Long userId, Pageable pageable) {
        List<Post> posts = queryFactory.select(post)
                .from(postLike)
                .join(postLike.post, post)
                .where(postLike.user.id.eq(userId))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(post.count())
                .from(postLike)
                .where(postLike.user.id.eq(userId))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(posts, pageable, total);
    }
}
