package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.DaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayScheduleRepository extends JpaRepository <DaySchedule, Integer> {
}
