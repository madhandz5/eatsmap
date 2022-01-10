package com.eatsmap.module.calendar.dto;

import com.eatsmap.module.calendar.Calendar;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateCalendarResponse {

    private String title;
    private LocalDate date;
    private LocalTime time;
    private String resName;

    public static CreateCalendarResponse createResponse(Calendar calendar) {
        return CreateCalendarResponse.builder()
                .title(calendar.getTitle())
                .date(calendar.getDate())
                .time(calendar.getTime())
                .resName(calendar.getRestaurant().getResName())
                .build();
    }
}
