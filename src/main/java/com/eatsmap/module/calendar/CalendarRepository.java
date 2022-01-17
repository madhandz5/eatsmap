package com.eatsmap.module.calendar;

import com.eatsmap.module.category.CategoryRepositoryExtension;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository <Calendar, Long>, CalendarRepositoryExtension {

    List<Calendar> findByCreatedBy(Long createdBy);
}
