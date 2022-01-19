package com.eatsmap.module.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewRepositoryExtensionImpl extends QuerydslRepositorySupport implements ReviewRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

//    @Override
//    public List<Review> findTimeline() {
//        return queryFactory
//                .select(review);
//    }
}
