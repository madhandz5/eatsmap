package com.eatsmap.module.calendar;


import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendar.dto.CreateCalendarResponse;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;


    @Transactional
    public CreateCalendarResponse createSchedule(@Valid @RequestBody CreateCalendarRequest request) {

        Calendar calender = calendarRepository.save(Calendar.createCalendar(request));
        return CreateCalendarResponse.createResponse(calender);

    }







}
