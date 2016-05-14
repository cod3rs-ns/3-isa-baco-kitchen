package com.bacovakuhinja.service;

import com.bacovakuhinja.model.MenuItem;

import java.util.Collection;

public interface MenuItemService {

    public Collection <MenuItem> findAll();

    public MenuItem findOne(Integer id);

    public MenuItem create(MenuItem menuItem);

    public MenuItem update(MenuItem menuItem);

    public void delete(Integer id);

    public Collection <MenuItem> findAllByRestaurant(Integer restaurantId);

}
