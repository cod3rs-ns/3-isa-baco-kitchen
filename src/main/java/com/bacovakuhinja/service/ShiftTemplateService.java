package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ShiftTemplate;

import java.util.Collection;

public interface ShiftTemplateService {

    public Collection <ShiftTemplate> findAll();

    public ShiftTemplate findOne(Integer shiftId);

    public ShiftTemplate create(ShiftTemplate shiftTemplate);

    public ShiftTemplate update(ShiftTemplate shiftTemplate);

    public void delete(Integer shiftId);

    public Collection <ShiftTemplate> findByRestaurant(Integer restaurantId);

}
