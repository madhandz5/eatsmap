package com.eatsmap.module.calendar;

import com.eatsmap.module.category.CategoryRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository <Calendar, Long>, CategoryRepositoryExtension {


}
