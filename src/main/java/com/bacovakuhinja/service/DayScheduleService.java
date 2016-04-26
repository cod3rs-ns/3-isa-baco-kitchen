package com.bacovakuhinja.service;

import com.bacovakuhinja.model.DaySchedule;

import java.util.Collection;

/**
 * Created by Bojan on 25-Apr-16.
 */
public interface DayScheduleService {
    public Collection<DaySchedule> findAll();

    public DaySchedule findOne(Integer eId);

    public DaySchedule create(DaySchedule e);

    public DaySchedule update(DaySchedule e);

    public void delete(Integer eId);
}
