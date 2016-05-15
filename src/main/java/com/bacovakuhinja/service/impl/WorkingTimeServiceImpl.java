package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.WorkingTime;
import com.bacovakuhinja.repository.WorkingTimeRepository;
import com.bacovakuhinja.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public Collection <WorkingTime> findAll() {
        return workingTimeRepository.findAll();
    }

    @Override
    public WorkingTime findOne(Integer workingTimeId) {
        return workingTimeRepository.findOne(workingTimeId);
    }

    @Override
    public WorkingTime create(WorkingTime workingTime) {
        return workingTimeRepository.save(workingTime);
    }

    @Override
    public WorkingTime update(WorkingTime workingTime) {
        WorkingTime workingTimePersistent = findOne(workingTime.getWorkingTimeId());
        if (workingTimePersistent == null) return null;
        return workingTimeRepository.save(workingTime);
    }

    @Override
    public WorkingTime findByRestaurant(Integer restaurantId) {
        Collection <WorkingTime> allWorkingTimes = workingTimeRepository.findAll();
        for (WorkingTime workingTime : allWorkingTimes) {
            if (workingTime.getRestaurantId() == restaurantId) {
                return workingTime;
            }
        }
        return null;
    }

}
