package com.eatsmap.module.calendar;


import java.util.List;

public interface CalendarRepositoryExtension {


     List<Calendar> findByMember(Long id);
}
