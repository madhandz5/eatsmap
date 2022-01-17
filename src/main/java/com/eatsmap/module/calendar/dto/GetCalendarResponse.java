package com.eatsmap.module.calendar.dto;

import com.eatsmap.module.calendar.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@Builder(access = AccessLevel.PRIVATE)
public class GetCalendarResponse {

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time;

    public static GetCalendarResponse createResponse(Calendar calendar) {
        return GetCalendarResponse.builder()
                .title(calendar.getTitle())
                .date(calendar.getDate())
                .time(calendar.getTime())
                .build();
    }

}
