package com.elwynn94.lolcome.repository;

import com.elwynn94.lolcome.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.elwynn94.lolcome.entity.QComment.comment;
import static com.elwynn94.lolcome.entity.QCommentLike.commentLike;

@Slf4j
@RequiredArgsConstructor
public class CommentLikeRepositoryCustomImpl implements CommentLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findLikedCommentsByUserId(Long userId, Pageable pageable) {
        List<Comment> comments = queryFactory.select(comment)
                .from(commentLike, comment)
                .where(commentLike.user.id.eq(userId))
                .orderBy(comment.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory.select(comment.count())
                .from(commentLike)
                .where(commentLike.user.id.eq(userId))
                .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(comments, pageable, total);
    }
}