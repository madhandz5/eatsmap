package com.eatsmap.module.restaurant;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RestaurantRepositoryExtensionImpl extends QuerydslRepositorySupport implements RestaurantRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public RestaurantRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Restaurant.class);
        this.queryFactory = queryFactory;
    }
}
