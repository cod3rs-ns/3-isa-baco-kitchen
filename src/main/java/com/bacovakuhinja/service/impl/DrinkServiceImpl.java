package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Drink;
import com.bacovakuhinja.repository.DrinkRepository;
import com.bacovakuhinja.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DrinkServiceImpl implements DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public Collection <Drink> findAll() {
        return drinkRepository.findAll();
    }

    @Override
    public Drink findOne(Integer id) {
        return drinkRepository.findOne(id);
    }

    @Override
    public Drink create(Drink drink) {
        return drinkRepository.save(drink);
    }

    @Override
    public Drink update(Drink drink) {
        Drink drinkPersistent = findOne(drink.getDrinkId());
        if (drinkPersistent == null) return null;
        return drinkRepository.save(drink);
    }

    @Override
    public void delete(Integer id) {
        drinkRepository.delete(id);
    }

    @Override
    public Collection <Drink> findAllByRestaurant(Integer restaurantId) {
        Collection <Drink> allDrinks = drinkRepository.findAll();
        Collection <Drink> restaurantDrinks = new ArrayList <Drink>();
        for (Drink drink : allDrinks) {
            if (drink.getRestaurant().getRestaurantId() == restaurantId) {
                restaurantDrinks.add(drink);
            }
        }
        return restaurantDrinks;
    }
}
