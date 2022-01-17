package com.eatsmap.module.calendarMemberHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarMemberHistoryRepository extends JpaRepository<CalendarMemberHistory, Long>, CalendarMemberHistoryRepositoryExtension {
}
