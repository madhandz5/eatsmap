package com.eatsmap.module.calendar;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendar.dto.CreateCalendarResponse;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


}
