package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantManager;

import java.util.Collection;

public interface RestaurantManagerService {

    public Collection <RestaurantManager> findAll();

    public RestaurantManager findOne(Integer rmId);

    public RestaurantManager create(RestaurantManager rm);

    public RestaurantManager update(RestaurantManager rm);

    public void delete(Integer rmId);
}
