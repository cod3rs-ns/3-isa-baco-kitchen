package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantProvider;

import java.util.Collection;

public interface RestaurantProviderService {

    Collection <RestaurantProvider> findAll();

    RestaurantProvider findOne(Integer providerId);

    RestaurantProvider create(RestaurantProvider provider);

    RestaurantProvider update(RestaurantProvider provider);

    void delete(Integer providerId);
}
