package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MenuItemRepository extends JpaRepository <MenuItem, Integer> {

    Collection <MenuItem> findByRestaurant_RestaurantId(Integer id);

    Collection <MenuItem> findByTypeAndDeletedAndRestaurant_RestaurantId(String type, boolean deleted, Integer id);
}
