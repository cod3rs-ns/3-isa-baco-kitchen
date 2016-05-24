package com.bacovakuhinja.service;


import com.bacovakuhinja.model.DailySchedule;

import java.util.Collection;
import java.util.Date;

public interface DailyScheduleService {

    Collection <DailySchedule> findAll();

    DailySchedule findOne(Integer eId);

    DailySchedule create(DailySchedule e);

    DailySchedule update(DailySchedule e);

    void delete(Integer eId);

    Collection <DailySchedule> findByRestaurant(Integer restaurantId);

    Collection <DailySchedule> findByEmployee(Integer employeeId);

    Collection <DailySchedule> findByRestaurantRegion(Integer regionId);

    Collection <DailySchedule> findByDay(Date day);

    DailySchedule findScheduleByRegionForNow(Integer regionId);

    DailySchedule findScheduleByEmployeeForNow(Integer employeeId);

}

