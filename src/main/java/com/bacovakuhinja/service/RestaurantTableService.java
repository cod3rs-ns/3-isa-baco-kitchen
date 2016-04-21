package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantTable;

import java.util.Collection;

public interface RestaurantTableService {

    public Collection <RestaurantTable> findAll();

    public RestaurantTable findOne(Integer id);

    public RestaurantTable create(RestaurantTable restaurantTable);

    public RestaurantTable update(RestaurantTable restaurantTable);

    public void delete(Integer id);

    public Collection <RestaurantTable> findAllByRestaurant(Integer restaurantId);

    public Collection <RestaurantTable> findAllByRegion(Integer regionId);

    public Integer getNextId();

}
