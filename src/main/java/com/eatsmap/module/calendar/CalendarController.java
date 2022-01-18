package com.eatsmap.module.calendar;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.calendar.dto.*;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/calendar")
public class CalendarController {

    private final CalendarService calenderService;

    @PostMapping(path ="/schedule/create")
    public ResponseEntity scheduleCreation(@RequestBody CreateCalendarRequest request, @CurrentMember Member member) {

        CreateCalendarResponse data = calenderService.createSchedule(request,member);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/")
    public ResponseEntity calendar(@CurrentMember Member member){
        System.out.println("헬로");
        ReturnCalendars data = calenderService.getCalendar(member);

        System.out.println(data.toString());
        CommonResponse response = CommonResponse.createResponse(true,data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/schedule/modify")
    public ResponseEntity calendarModify(@RequestBody ModifyCalendarRequest request, @CurrentMember Member member){
        System.out.println("동작 확인1");
        ModifyCalendarResponse data = calenderService.calendarUpdate(request,member);

        CommonResponse response = CommonResponse.createResponse(true,data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
