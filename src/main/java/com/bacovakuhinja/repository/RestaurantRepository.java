package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository <Restaurant, Integer> {
}
