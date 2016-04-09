package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.RestaurantManager;
import com.bacovakuhinja.repository.RestaurantManagerRepository;
import com.bacovakuhinja.service.RestaurantManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RestaurantManagerServiceImpl implements RestaurantManagerService {

    @Autowired
    private RestaurantManagerRepository rmRepository;

    @Override
    public Collection <RestaurantManager> findAll() {
        return rmRepository.findAll();
    }

    @Override
    public RestaurantManager findOne(Integer rmId) {
        return rmRepository.findOne(rmId);
    }

    @Override
    public RestaurantManager create(RestaurantManager rm) {
        return rmRepository.save(rm);
    }

    @Override
    public RestaurantManager update(RestaurantManager rm) {
        RestaurantManager rmPersistent = findOne(rm.getUserId());
        if (rmPersistent == null) return null;
        return rmRepository.save(rm);
    }

    @Override
    public void delete(Integer rmId) {
        rmRepository.delete(rmId);
    }
}
