package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantProvider;

import java.util.Collection;

public interface RestaurantProviderService {

    public Collection <RestaurantProvider> findAll();

    public RestaurantProvider findOne(Integer providerId);

    public RestaurantProvider create(RestaurantProvider provider);

    public RestaurantProvider update(RestaurantProvider provider);

    public void delete(Integer providerId);
}
