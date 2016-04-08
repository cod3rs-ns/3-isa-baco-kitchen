package com.bacovakuhinja.service;

import com.bacovakuhinja.model.RestaurantProvider;

import java.util.Collection;

/**
 * Created by St_Keky on 7.4.2016..
 */
public interface RestaurantProviderService {

    public Collection<RestaurantProvider> findAll();

    public RestaurantProvider create(RestaurantProvider provider);

}
