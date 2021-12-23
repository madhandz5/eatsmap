package com.eatsmap.module.groupMemberHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberGroupHistoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements MemberGroupHistoryRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public MemberGroupHistoryRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(MemberGroupHistory.class);
        this.queryFactory = queryFactory;
    }

}
