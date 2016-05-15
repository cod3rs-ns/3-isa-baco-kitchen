package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ShiftTemplate;
import com.bacovakuhinja.repository.ShiftTemplateRepository;
import com.bacovakuhinja.service.ShiftTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ShiftTemplateServiceImpl implements ShiftTemplateService {

    @Autowired
    private ShiftTemplateRepository shiftTemplateRepository;

    @Override
    public Collection <ShiftTemplate> findAll() {
        return shiftTemplateRepository.findAll();
    }

    @Override
    public ShiftTemplate findOne(Integer shiftId) {
        return shiftTemplateRepository.findOne(shiftId);
    }

    @Override
    public ShiftTemplate create(ShiftTemplate shiftTemplate) {
        return shiftTemplateRepository.save(shiftTemplate);
    }

    @Override
    public ShiftTemplate update(ShiftTemplate shiftTemplate) {
        ShiftTemplate shiftTemplatePersistent = findOne(shiftTemplate.getShiftId());
        if (shiftTemplatePersistent == null) return null;
        return shiftTemplateRepository.save(shiftTemplate);
    }

    @Override
    public void delete(Integer shiftId) {
        shiftTemplateRepository.delete(shiftId);
    }

    @Override
    public Collection <ShiftTemplate> findByRestaurant(Integer restaurantId) {
        Collection <ShiftTemplate> allShiftTemplates = shiftTemplateRepository.findAll();
        Collection <ShiftTemplate> restaurantShiftTemplates = new ArrayList <ShiftTemplate>();
        for (ShiftTemplate shiftTemplate : allShiftTemplates) {
            if (shiftTemplate.getRestaurantId() == restaurantId) {
                restaurantShiftTemplates.add(shiftTemplate);
            }
        }
        return restaurantShiftTemplates;
    }
}
