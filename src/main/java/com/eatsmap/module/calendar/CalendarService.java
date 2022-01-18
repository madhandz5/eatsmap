package com.eatsmap.module.calendar;


import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.calendar.dto.*;
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



    @Transactional
    public ModifyCalendarResponse calendarUpdate(ModifyCalendarRequest request, Member member) {
        System.out.println("test");
        Calendar calendar = calendarRepository.findByIdAndCreatedBy(request.getCalendarId(),member.getId());

        System.out.println(calendar.getCalendarMembers().toString());

        System.out.println("동작 확인");
        if (calendar.equals(null)){
            System.out.println("에러 확인");
            throw new CommonException(ErrorCode.CALENDAR_NOT_FOUND);
        }

        calendar.calendarModify(request);

        //followMember삭제
        calendarMemberHistoryService.deleteHistory(request.getCalendarId());
        System.out.println("삭제 확인");
        //수정된 맴버 등록
        for(Long followMember : request.getFollowMember()){
            if (followMember.equals(member.getId())){
                System.out.println(followMember);
                throw new CommonException(ErrorCode.CANNOT_INVITE_SELF);
            }
            System.out.println("수정맴버 확인");
            calendarMemberHistoryService.createCalendar(followMember, calendar);
        }
        System.out.println(" 확인");
        return ModifyCalendarResponse.createResponse(calendarRepository.save(calendar));


    }
}
