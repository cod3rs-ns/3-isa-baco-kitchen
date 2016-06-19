package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface RestaurantRepository extends JpaRepository <Restaurant, Integer> {

    Collection<Restaurant> findRestaurantByNameContainsOrTypeStartsWithAllIgnoreCase(String name, String type);
}
