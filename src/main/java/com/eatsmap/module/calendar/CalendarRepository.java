package com.eatsmap.module.calendar;

import com.eatsmap.module.category.CategoryRepositoryExtension;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository <Calendar, Long>, CalendarRepositoryExtension {

    Calendar findByIdAndCreatedBy(Long id, Long createdBy);



}
