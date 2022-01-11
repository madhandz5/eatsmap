package com.eatsmap.module.calendar;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendar.dto.CreateCalendarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/calendar")
public class CalendarController {

    private final CalendarService calenderService;

    @PostMapping("/schedule-create")
    public ResponseEntity scheduleCreation(@RequestBody CreateCalendarRequest request) {
        System.out.println(request);
        CreateCalendarResponse data = calenderService.createSchedule(request);
        System.out.println("데이터");
        System.out.println(data);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
