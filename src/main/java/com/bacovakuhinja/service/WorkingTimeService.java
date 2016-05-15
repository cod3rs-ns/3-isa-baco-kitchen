package com.bacovakuhinja.service;

import com.bacovakuhinja.model.WorkingTime;

import java.util.Collection;

public interface WorkingTimeService {

    public Collection<WorkingTime> findAll();

    public WorkingTime findOne(Integer workingTimeId);

    public WorkingTime create(WorkingTime workingTime);

    public WorkingTime update(WorkingTime workingTime);

    public WorkingTime findByRestaurant(Integer restaurantId);

}
