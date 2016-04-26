package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.DaySchedule;
import com.bacovakuhinja.repository.DayScheduleRepository;
import com.bacovakuhinja.service.DayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DayScheduleServiceImpl implements DayScheduleService {
    @Autowired
    private DayScheduleRepository dayScheduleRepository;

    @Override
    public Collection<DaySchedule> findAll() {
        return dayScheduleRepository.findAll();
    }

    @Override
    public DaySchedule findOne(Integer dId) {
        return dayScheduleRepository.findOne(dId);
    }

    @Override
    public DaySchedule create(DaySchedule d) {
        return dayScheduleRepository.save(d);
    }

    @Override
    public DaySchedule update(DaySchedule d) {
        DaySchedule dayPersistent = dayScheduleRepository.findOne(d.getDayScheduleId());
        if (dayPersistent == null) return null;
        return dayScheduleRepository.save(d);
    }

    @Override
    public void delete(Integer dId) {
        dayScheduleRepository.delete(dId);
    }

}
