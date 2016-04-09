package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.repository.RestaurantRepository;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Collection <Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findOne(Integer restaurantId) {
        return restaurantRepository.findOne(restaurantId);
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        Restaurant restaurantPersistent = findOne(restaurant.getRestaurantId());
        if (restaurantPersistent == null) return null;
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void delete(Integer restaurantId) {
        restaurantRepository.delete(restaurantId);
    }
}
