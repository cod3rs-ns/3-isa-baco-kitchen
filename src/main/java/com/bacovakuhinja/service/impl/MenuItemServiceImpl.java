package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.MenuItem;
import com.bacovakuhinja.repository.MenuItemRepository;
import com.bacovakuhinja.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public Collection<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem findOne(Integer id) {
        return menuItemRepository.findOne(id);
    }

    @Override
    public MenuItem create(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public Collection <MenuItem> findAllByRestaurant(Integer restaurantId) {
        Collection<MenuItem> allMenuItem = menuItemRepository.findAll();
        Collection<MenuItem> restaurantMenuItem = new ArrayList <MenuItem>();
        for (MenuItem menuItem : allMenuItem) {
            if(menuItem.getRestaurant().getRestaurantId() == restaurantId){
                restaurantMenuItem.add(menuItem);
            }
        }
        return restaurantMenuItem;
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        MenuItem menuItemPersistent = findOne(menuItem.getMenuItemId());
        if (menuItemPersistent == null) return null;
        return menuItemRepository.save(menuItem);
    }

    @Override
    public void delete(Integer id) {
        menuItemRepository.delete(id);
    }
}
