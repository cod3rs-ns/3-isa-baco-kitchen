package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Restaurant;

import java.util.Collection;

public interface RestaurantService {

    public Collection <Restaurant> findAll();

    public Restaurant findOne(Integer restaurantId);

    public Restaurant create(Restaurant restaurant);

    public Restaurant update(Restaurant restaurant);

    public void delete(Integer restaurantId);

}
