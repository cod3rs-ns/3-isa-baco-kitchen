package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.MenuItem;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.MenuItemService;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/menu_items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItems() {
        Collection <MenuItem> menuItem = menuService.findAll();
        return new ResponseEntity <Collection <MenuItem>>(menuItem, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/menu_items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> getMenuItem(@PathVariable("id") Integer id) {
        MenuItem menuItem = menuService.findOne(id);
        return new ResponseEntity <MenuItem>(menuItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/menu_items/r={rst_id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> createMenuItem(@RequestBody MenuItem menuItem, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        menuItem.setRestaurant(restaurant);
        MenuItem created = menuService.create(menuItem);
        return new ResponseEntity <MenuItem>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/menu_items/r={rst_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> updateFood(@RequestBody MenuItem menuItem, @PathVariable("rst_id") Integer id) {
        menuItem.setRestaurant(restaurantService.findOne(id));
        MenuItem updated = menuService.update(menuItem);
        if (updated == null) {
            return new ResponseEntity <MenuItem>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <MenuItem>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/menu_items/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <MenuItem> deleteFood(@PathVariable("id") Integer id) {
        MenuItem mi = menuService.findOne(id);
        mi.setDeleted(true);
        menuService.update(mi);
        return new ResponseEntity <MenuItem>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/menu_items/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItemsByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <MenuItem> restaurantMenuItem = menuService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <MenuItem>>(restaurantMenuItem, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/menu_items/r={rest_id}/t={t_str}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItemsByTypeAndRestaurant(@PathVariable("rest_id") Integer restaurantId, @PathVariable("t_str") String type) {
        // Always deleted = false, to retrieve only ones that are not deleted
        Collection <MenuItem> restaurantMenuItem = menuService.findByTypeAndDeletedAndRestaurant(type, false, restaurantId);
        return new ResponseEntity <Collection <MenuItem>>(restaurantMenuItem, HttpStatus.OK);
    }

}
