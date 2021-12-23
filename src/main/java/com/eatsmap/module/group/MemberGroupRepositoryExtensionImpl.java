package com.eatsmap.module.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberGroupRepositoryExtensionImpl extends QuerydslRepositorySupport implements MemberGroupRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public MemberGroupRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(MemberGroup.class);
        this.queryFactory = queryFactory;
    }

}
