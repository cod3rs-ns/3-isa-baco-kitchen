package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantRegion;

import java.util.Collection;

public interface RestaurantRegionService {

    Collection <RestaurantRegion> findAll();

    RestaurantRegion findOne(Integer id);

    RestaurantRegion create(RestaurantRegion restaurantRegion);

    RestaurantRegion update(RestaurantRegion restaurantRegion);

    void delete(Integer id);

    Collection <RestaurantRegion> findAllByRestaurant(Integer restaurantId);

}
