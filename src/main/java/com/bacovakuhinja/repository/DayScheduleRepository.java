package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DayScheduleRepository extends JpaRepository <DailySchedule, Integer> {
    public ArrayList<DailySchedule> findByDayAndRegion_RegionIdAndStartHoursLessThanEqualAndEndHoursGreaterThanEqual(Date date, Integer region, Integer sh, Integer eh);
}
