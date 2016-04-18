package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Food;
import com.bacovakuhinja.repository.FoodRepository;
import com.bacovakuhinja.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Collection<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food findOne(Integer id) {
        return foodRepository.findOne(id);
    }

    @Override
    public Food create(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Collection <Food> findAllByRestaurant(Integer restaurantId) {
        Collection<Food> allFood = foodRepository.findAll();
        Collection<Food> restaurantFood = new ArrayList <Food>();
        for (Food food : allFood) {
            if(food.getRestaurant().getRestaurantId() == restaurantId){
                restaurantFood.add(food);
            }
        }
        return restaurantFood;
    }

    @Override
    public Food update(Food food) {
        Food foodPersistent = findOne(food.getFoodId());
        if (foodPersistent == null) return null;
        return foodRepository.save(food);
    }

    @Override
    public void delete(Integer id) {
        foodRepository.delete(id);
    }
}
