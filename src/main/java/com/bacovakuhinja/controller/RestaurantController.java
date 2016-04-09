package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/restaurants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Restaurant>> getRestaurants() {
        Collection <Restaurant> restaurants = restaurantService.findAll();
        return new ResponseEntity <Collection <Restaurant>>(restaurants, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/restaurants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Restaurant> getRestaurant(@PathVariable("id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        return new ResponseEntity <Restaurant>(restaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/restaurants",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.create(restaurant);
        return new ResponseEntity <Restaurant>(createdRestaurant, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/restaurants",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Restaurant> updateRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.update(restaurant);
        if (updatedRestaurant == null) {
            return new ResponseEntity <Restaurant>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Restaurant>(updatedRestaurant, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/restaurants/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <Restaurant> deleteRestaurant(@PathVariable("id") Integer id) {
        restaurantService.delete(id);
        return new ResponseEntity <Restaurant>(HttpStatus.NO_CONTENT);
    }
}
