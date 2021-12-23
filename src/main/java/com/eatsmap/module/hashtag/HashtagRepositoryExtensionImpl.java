package com.eatsmap.module.hashtag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HashtagRepositoryExtensionImpl extends QuerydslRepositorySupport implements HashtagRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public HashtagRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Hashtag.class);
        this.queryFactory = queryFactory;
    }

}
