package com.bacovakuhinja.service;

import com.bacovakuhinja.model.DailySchedule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public interface DailyScheduleService {

    public Collection <DailySchedule> findAll();

    public DailySchedule findOne(Integer eId);

    public DailySchedule create(DailySchedule e);

    public DailySchedule update(DailySchedule e);

    public void delete(Integer eId);

    public Collection <DailySchedule> findByRestaurant(Integer restaurantId);

    public Collection <DailySchedule> findByEmployee(Integer employeeId);

    public Collection <DailySchedule> findByRestaurantRegion(Integer regionId);

    public Collection <DailySchedule> findByDay(Date day);

    public DailySchedule findScheduleByRegionForNow(Integer regionId);

    public DailySchedule findScheduleByEmployeeForNow(Integer employeeId);
}
