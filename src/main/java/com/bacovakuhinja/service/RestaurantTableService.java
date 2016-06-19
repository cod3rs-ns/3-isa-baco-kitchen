package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantTable;

import java.util.Collection;

public interface RestaurantTableService {

    Collection <RestaurantTable> findAll();

    RestaurantTable findOne(Integer id);

    RestaurantTable create(RestaurantTable restaurantTable);

    RestaurantTable update(RestaurantTable restaurantTable);

    void delete(Integer id);

    Collection <RestaurantTable> findAllByRestaurant(Integer restaurantId);

    //FIXME @Keky
    Collection <RestaurantTable> findAllByRegion(Integer regionId);

    Integer getNextId();

}
