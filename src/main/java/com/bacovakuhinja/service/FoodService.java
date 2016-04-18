package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Food;

import java.util.Collection;

public interface FoodService {

    public Collection <Food> findAll();

    public Food findOne(Integer id);

    public Food create(Food food);

    public Food update(Food food);

    public void delete(Integer id);

    public Collection <Food> findAllByRestaurant(Integer restaurantId);

}
