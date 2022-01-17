package com.eatsmap.module.calendarMemberHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CalendarMemberHistoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements CalendarMemberHistoryRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public CalendarMemberHistoryRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(CalendarMemberHistory.class);
        this.queryFactory = queryFactory;
    }

}
