package com.eatsmap.module.calendar.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateCalendarRequest {

    @NotEmpty
    private String title;
    @NotEmpty
    private LocalDate date;
    @NotEmpty
    private LocalTime time;
    @NotEmpty
    private String resName;

}
