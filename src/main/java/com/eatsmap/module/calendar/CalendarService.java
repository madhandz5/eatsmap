package com.eatsmap.module.calendar;


import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendar.dto.CreateCalendarResponse;
import com.eatsmap.module.calendar.dto.GetCalendarResponse;
import com.eatsmap.module.calendar.dto.ReturnCalendars;
import com.eatsmap.module.calendarMemberHistory.CalendarMemberHistoryService;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            if (followMember.equals(member.getId())){
                System.out.println(followMember);
                throw new CommonException(ErrorCode.CANNOT_INVITE_SELF);
            }
            System.out.println("동작 확인");
            calendarMemberHistoryService.createCalendar(followMember, calender);
        }
        return CreateCalendarResponse.createResponse(calendarRepository.save(calender));

    }

    public ReturnCalendars getCalendar(Member member) {
        System.out.println(member);
        List<Calendar> calendars = calendarRepository.findByMember(member.getId());

        List<GetCalendarResponse> getCalendarResponses = new ArrayList<>();

        for (Calendar calendar: calendars) {
            getCalendarResponses.add(GetCalendarResponse.createResponse(calendar));
        }


        System.out.println(calendars.toString());

        return ReturnCalendars.createResponse(getCalendarResponses);
    }







}
