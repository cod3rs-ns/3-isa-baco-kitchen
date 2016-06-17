package com.bacovakuhinja.service;

import com.bacovakuhinja.model.WorkingTime;

import java.util.Collection;

public interface WorkingTimeService {

    Collection<WorkingTime> findAll();

    WorkingTime findOne(Integer workingTimeId);

    WorkingTime create(WorkingTime workingTime);

    WorkingTime update(WorkingTime workingTime);

    WorkingTime findByRestaurant(Integer restaurantId);

}
