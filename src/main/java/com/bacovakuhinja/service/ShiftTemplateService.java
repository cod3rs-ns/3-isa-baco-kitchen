package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ShiftTemplate;

import java.util.Collection;

public interface ShiftTemplateService {

    Collection <ShiftTemplate> findAll();

    ShiftTemplate findOne(Integer shiftId);

    ShiftTemplate create(ShiftTemplate shiftTemplate);

    ShiftTemplate update(ShiftTemplate shiftTemplate);

    void delete(Integer shiftId);

    Collection <ShiftTemplate> findByRestaurant(Integer restaurantId);

}
