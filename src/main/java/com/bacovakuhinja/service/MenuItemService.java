package com.bacovakuhinja.service;

import com.bacovakuhinja.model.MenuItem;

import java.util.Collection;

public interface MenuItemService {

    Collection <MenuItem> findAll();

    MenuItem findOne(Integer id);

    MenuItem create(MenuItem menuItem);

    MenuItem update(MenuItem menuItem);

    void delete(Integer id);

    Collection <MenuItem> findAllByRestaurant(Integer restaurantId);

    Collection <MenuItem> findByTypeAndRestaurant(String type, Integer restaurantId);

}
