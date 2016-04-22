package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository <RestaurantTable, Integer> {
}
