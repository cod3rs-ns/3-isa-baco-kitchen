package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Food;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.FoodService;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;


    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/food",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Food>> getFood() {
        Collection <Food> food = foodService.findAll();
        return new ResponseEntity <Collection <Food>>(food, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/food/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Food> getSingle(@PathVariable("id") Integer id) {
        Food food = foodService.findOne(id);
        return new ResponseEntity <Food>(food, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/food/r={rst_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Food> createFood(@RequestBody Food food, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        food.setRestaurant(restaurant);
        Food created = foodService.create(food);
        return new ResponseEntity <Food>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/food/r={rst_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Food> updateFood(@RequestBody Food food, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        food.setRestaurant(restaurant);
        Food updated = foodService.update(food);
        if (updated == null) {
            return new ResponseEntity <Food>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Food>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/food/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <Food> deleteFood(@PathVariable("id") Integer id) {
        foodService.delete(id);
        return new ResponseEntity <Food>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/food/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Food>> getFoodByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <Food> restaurantFood = foodService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <Food>>(restaurantFood, HttpStatus.OK);
    }

}
