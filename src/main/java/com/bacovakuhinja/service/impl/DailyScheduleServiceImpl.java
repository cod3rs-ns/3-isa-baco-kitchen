package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.DailySchedule;
import com.bacovakuhinja.repository.DayScheduleRepository;
import com.bacovakuhinja.service.DailyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
public class DailyScheduleServiceImpl implements DailyScheduleService {

    @Autowired
    private DayScheduleRepository dayScheduleRepository;

    @Override
    public Collection <DailySchedule> findAll() {
        return dayScheduleRepository.findAll();
    }

    @Override
    public DailySchedule findOne(Integer dId) {
        return dayScheduleRepository.findOne(dId);
    }

    @Override
    public DailySchedule create(DailySchedule d) {
        return dayScheduleRepository.save(d);
    }

    @Override
    public DailySchedule update(DailySchedule d) {
        DailySchedule dayPersistent = dayScheduleRepository.findOne(d.getDailyScheduleId());
        if (dayPersistent == null) return null;
        return dayScheduleRepository.save(d);
    }

    @Override
    public void delete(Integer dId) {
        dayScheduleRepository.delete(dId);
    }

    @Override
    public Collection <DailySchedule> findByRestaurant(Integer restaurantId) {
        Collection <DailySchedule> allSchedules = dayScheduleRepository.findAll();
        Collection <DailySchedule> restaurantSchedules = new ArrayList <DailySchedule>();
        for (DailySchedule dailySchedule : allSchedules) {
            if (dailySchedule.getRestaurantId() == restaurantId) {
                restaurantSchedules.add(dailySchedule);
            }
        }
        return restaurantSchedules;
    }

    @Override
    public Collection <DailySchedule> findByEmployee(Integer employeeId) {
        Collection <DailySchedule> allSchedules = dayScheduleRepository.findAll();
        Collection <DailySchedule> restaurantSchedules = new ArrayList <DailySchedule>();
        for (DailySchedule dailySchedule : allSchedules) {
            if (dailySchedule.getEmployee().getUserId() == employeeId) {
                restaurantSchedules.add(dailySchedule);
            }
        }
        return restaurantSchedules;
    }

    @Override
    public Collection <DailySchedule> findByRestaurantRegion(Integer regionId) {
        Collection <DailySchedule> allSchedules = dayScheduleRepository.findAll();
        Collection <DailySchedule> restaurantSchedules = new ArrayList <DailySchedule>();
        for (DailySchedule dailySchedule : allSchedules) {
            if (dailySchedule.getRegion().getRegionId() == regionId) {
                restaurantSchedules.add(dailySchedule);
            }
        }
        return restaurantSchedules;
    }

    @Override
    public Collection <DailySchedule> findByDay(Date day) {
        Collection <DailySchedule> allSchedules = dayScheduleRepository.findAll();
        Collection <DailySchedule> restaurantSchedules = new ArrayList <DailySchedule>();
        for (DailySchedule dailySchedule : allSchedules) {
            if (dailySchedule.getDay() == day) {
                restaurantSchedules.add(dailySchedule);
            }
        }
        return restaurantSchedules;
    }

    @Override
    public DailySchedule findScheduleByRegionForNow(Integer regionId) {
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        DailySchedule schedule = dayScheduleRepository.findByRegion_RegionIdAndMergedStartLessThanAndMergedEndGreaterThanEqual(regionId, date, date);
        return schedule;
    }

    @Override
    public DailySchedule findScheduleByEmployeeForNow(Integer employeeId) {
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        DailySchedule schedule = dayScheduleRepository.findByEmployee_UserIdAndMergedStartLessThanAndMergedEndGreaterThanEqual(employeeId, date, date);
        return schedule;
    }
}
