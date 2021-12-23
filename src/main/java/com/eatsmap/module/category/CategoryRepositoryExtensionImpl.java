package com.eatsmap.module.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements CategoryRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Category.class);
        this.queryFactory = queryFactory;
    }
}
