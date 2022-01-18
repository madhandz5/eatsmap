package com.eatsmap.module.calendar.dto;

import com.eatsmap.module.calendar.Calendar;
import com.eatsmap.module.calendarMemberHistory.CalendarMemberHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Data
@Builder(access = AccessLevel.PRIVATE)
public class ModifyCalendarResponse {

    private Long calendarId;

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time;

    private LocalDateTime modifyAt;

    private List<CalendarMemberHistory> calendarMembers;

    public static ModifyCalendarResponse createResponse(Calendar calendar) {
        return ModifyCalendarResponse.builder()
                .calendarId(calendar.getId())
                .title(calendar.getTitle())
                .date(calendar.getDate())
                .time(calendar.getTime())
                .modifyAt(LocalDateTime.now())
                .calendarMembers(calendar.getCalendarMembers())
                .build();
    }

}
