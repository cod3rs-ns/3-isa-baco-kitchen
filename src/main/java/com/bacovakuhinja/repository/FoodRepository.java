package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FoodRepository extends JpaRepository <Food, Integer> {
}
