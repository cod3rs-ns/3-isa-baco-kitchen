package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantRegion;

import java.util.Collection;

public interface RestaurantRegionService {

    public Collection <RestaurantRegion> findAll();

    public RestaurantRegion findOne(Integer id);

    public RestaurantRegion create(RestaurantRegion restaurantRegion);

    public RestaurantRegion update(RestaurantRegion restaurantRegion);

    public void delete(Integer id);

    public Collection <RestaurantRegion> findAllByRestaurant(Integer restaurantId);


}
