package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Restaurant;

import java.util.Collection;

public interface RestaurantService {

    Collection <Restaurant> findAll();

    Restaurant findOne(Integer restaurantId);

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    void delete(Integer restaurantId);

    Collection<Restaurant> getRestaurants(String query);

}
