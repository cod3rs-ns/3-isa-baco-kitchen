package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Drink;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface DrinkService {

    public Collection <Drink> findAll();

    public Drink findOne(Integer id);

    public Drink create(Drink drink);

    public Drink update(Drink drink);

    public void delete(Integer id);

    public Collection <Drink> findAllByRestaurant(Integer restaurantId);

}
