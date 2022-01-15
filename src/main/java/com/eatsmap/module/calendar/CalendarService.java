package com.eatsmap.module.calendar;


import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendar.dto.CreateCalendarResponse;
import com.eatsmap.module.calendarReviewHistory.CalendarMemberHistoryService;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarMemberHistoryService calendarMemberHistoryService;


    @Transactional
    public CreateCalendarResponse createSchedule(CreateCalendarRequest request, Member member) {

        Calendar calender = Calendar.createCalendar(request,member);

        for (Long followMember : request.getFollowMember()) {
            calendarMemberHistoryService.createCalendar(followMember, calender);
        }
        return CreateCalendarResponse.createResponse(calendarRepository.save(calender));

    }







}
