package com.eatsmap.module.calendarReviewHistory;

import com.eatsmap.module.group.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarMemberHistoryRepository extends JpaRepository<CalendarMemberHistory, Long>, CalendarMemberHistoryRepositoryExtension {



    int countByMemberGroup_Id(Long id);

}
