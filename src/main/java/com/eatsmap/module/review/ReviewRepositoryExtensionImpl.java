package com.eatsmap.module.review;

import com.eatsmap.module.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.eatsmap.module.review.QReview.review;

public class ReviewRepositoryExtensionImpl extends QuerydslRepositorySupport implements ReviewRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Review> findTimeline(Member member) {
        return queryFactory
                .selectFrom(review)
                .where(review.member.eq(member)
                                .or(review.privacy.eq(ReviewPrivacy.valueOf("PUBLIC"))
                                )
                    , review.deleted.eq(false))
                .fetch();
    }
}
