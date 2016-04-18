package com.bacovakuhinja.controller;


import com.bacovakuhinja.model.Drink;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.DrinkService;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/drinks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Drink>> getDrinks() {
        Collection <Drink> drinks = drinkService.findAll();
        return new ResponseEntity <Collection <Drink>>(drinks, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/drinks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Drink> getDrink(@PathVariable("id") Integer id) {
        Drink drink = drinkService.findOne(id);
        return new ResponseEntity <Drink>(drink, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/drink/r={rst_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Drink> createDrink(@RequestBody Drink drink, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        drink.setRestaurant(restaurant);
        Drink created = drinkService.create(drink);
        return new ResponseEntity <Drink>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/drinks/r={rst_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Drink> updateDrink(@RequestBody Drink drink, @PathVariable("rst_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        drink.setRestaurant(restaurant);
        Drink updated = drinkService.update(drink);
        if (updated == null) {
            return new ResponseEntity <Drink>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Drink>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/drinks/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <Drink> deleteDrink(@PathVariable("id") Integer id) {
        drinkService.delete(id);
        return new ResponseEntity <Drink>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/drinks/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Drink>> getDrinksByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <Drink> restaurantDrinks = drinkService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <Drink>>(restaurantDrinks, HttpStatus.OK);
    }

}
