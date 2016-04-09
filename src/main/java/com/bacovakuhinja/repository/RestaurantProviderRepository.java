package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.RestaurantProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantProviderRepository extends
        JpaRepository <RestaurantProvider, Integer> {
}
