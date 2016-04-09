package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantManagerRepository extends JpaRepository <RestaurantManager, Integer> {
}
