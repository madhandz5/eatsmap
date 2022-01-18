package com.eatsmap.module.calendar;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.eatsmap.module.calendar.QCalendar.calendar;
import static com.eatsmap.module.calendarMemberHistory.QCalendarMemberHistory.calendarMemberHistory;
import java.util.List;

public class CalendarRepositoryExtensionImpl extends QuerydslRepositorySupport implements CalendarRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public CalendarRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Calendar.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Calendar> findByMember(Long id) {
        return queryFactory
                .select(calendar)
                .from(calendarMemberHistory)
                .join(calendarMemberHistory.calendar,calendar)
                .where(calendarMemberHistory.member.id.eq(id).or(calendar.createdBy.eq(id)))
                .fetch();
    }




}
