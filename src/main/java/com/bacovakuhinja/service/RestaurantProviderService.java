package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantProvider;

import java.util.Collection;

public interface RestaurantProviderService {

    public Collection<RestaurantProvider> findAll();

    public RestaurantProvider create(RestaurantProvider provider);

}
