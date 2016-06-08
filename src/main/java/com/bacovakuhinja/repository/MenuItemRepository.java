package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.MenuItem;
import com.bacovakuhinja.model.OfferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface MenuItemRepository extends JpaRepository <MenuItem, Integer> {
    Collection<MenuItem> findByRestaurant_RestaurantId(Integer resId);

    Collection <MenuItem> findByTypeAndRestaurant_RestaurantId(String type, Integer id);
}
