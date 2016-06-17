package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantManager;

import java.util.Collection;

public interface RestaurantManagerService {

    Collection <RestaurantManager> findAll();

    RestaurantManager findOne(Integer rmId);

    RestaurantManager create(RestaurantManager rm);

    RestaurantManager update(RestaurantManager rm);

    void delete(Integer rmId);
}
