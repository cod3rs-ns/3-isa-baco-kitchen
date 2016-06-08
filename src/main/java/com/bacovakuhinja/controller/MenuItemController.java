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
    private MenuItemService menuItemService;


    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/menu_items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItems() {
        Collection <MenuItem> menuItem = menuItemService.findAll();
        return new ResponseEntity <Collection <MenuItem>>(menuItem, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/menu_items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> getMenuItem(@PathVariable("id") Integer id) {
        MenuItem menuItem = menuItemService.findOne(id);
        return new ResponseEntity <MenuItem>(menuItem, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/menu_items/r={rst_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> createMenuItem(@RequestBody MenuItem menuItem, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        menuItem.setRestaurant(restaurant);
        MenuItem created = menuItemService.create(menuItem);
        return new ResponseEntity <MenuItem>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/menu_items",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <MenuItem> updateFood(@RequestBody MenuItem menuItem) {
        MenuItem updated = menuItemService.update(menuItem);
        if (updated == null) {
            return new ResponseEntity <MenuItem>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <MenuItem>(updated, HttpStatus.OK);
    }

    // TODO Should be implemented logical remove
    @RequestMapping(
            value = "/api/menu_items/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <MenuItem> deleteFood(@PathVariable("id") Integer id) {
        menuItemService.delete(id);
        return new ResponseEntity <MenuItem>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/menu_items/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItemsByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <MenuItem> restaurantMenuItem = menuItemService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <MenuItem>>(restaurantMenuItem, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/menu_items/r={rest_id}/t={t_str}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <MenuItem>> getMenuItemsByTypeAndRestaurant(@PathVariable("rest_id") Integer rid, @PathVariable("t_str") String t_str) {
        Collection <MenuItem> restaurantMenuItem = menuItemService.findByTypeAndRestaurant(t_str, rid);
        return new ResponseEntity <Collection <MenuItem>>(restaurantMenuItem, HttpStatus.OK);
    }

}
