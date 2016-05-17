package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayScheduleRepository extends JpaRepository <DailySchedule, Integer> {
}
