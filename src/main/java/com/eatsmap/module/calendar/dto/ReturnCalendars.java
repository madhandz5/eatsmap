package com.eatsmap.module.calendar.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ReturnCalendars {

    private List<GetCalendarResponse> calendars;


    public static ReturnCalendars createResponse(List<GetCalendarResponse> calendars){
        return ReturnCalendars.builder()
                .calendars(calendars)
                .build();

    }



}
