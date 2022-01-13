package com.eatsmap.module.calendar.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateCalendarRequest {

    private String title;
    private String date;
    private String time;
    private List<Long> followMember;


}
