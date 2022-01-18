package com.eatsmap.module.calendarMemberHistory;

import com.eatsmap.module.calendar.Calendar;
import com.eatsmap.module.calendar.CalendarRepository;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarMemberHistoryService {

    private final CalendarMemberHistoryRepository calendarMemberHistoryRepository;
    private final CalendarRepository calendarRepository;
    private final MemberService memberService;

    //캘린더 등록
    @Transactional
    public void createCalendar(Long followMember, Calendar calendar) {
        Member member = memberService.getMember(followMember);
        CalendarMemberHistory calendarMemberHistory = CalendarMemberHistory.createMemberGroupHistory(calendar, member);
        calendarMemberHistoryRepository.save(calendarMemberHistory);
    }

    @Transactional
    public void deleteHistory(Long calendarId) {
        int result = calendarMemberHistoryRepository.deleteByCalendarId(calendarId);

    }




}
