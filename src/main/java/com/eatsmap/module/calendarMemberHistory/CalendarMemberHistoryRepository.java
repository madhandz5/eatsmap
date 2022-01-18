package com.eatsmap.module.calendarMemberHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CalendarMemberHistoryRepository extends JpaRepository<CalendarMemberHistory, Long>, CalendarMemberHistoryRepositoryExtension {

    @Modifying
    @Query("DELETE from CalendarMemberHistory c where c.calendar.id = ?1")
    int deleteByCalendarId(Long calendarId);
}
