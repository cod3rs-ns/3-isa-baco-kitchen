package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.RestaurantRegion;
import com.bacovakuhinja.service.RestaurantRegionService;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RestaurantRegionController {

    @Autowired
    private RestaurantRegionService regionService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/regions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantRegion>> getRegions() {
        Collection <RestaurantRegion> regions = regionService.findAll();
        return new ResponseEntity <Collection <RestaurantRegion>>(regions, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/regions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantRegion> getRegion(@PathVariable("id") Integer id) {
        RestaurantRegion region = regionService.findOne(id);
        return new ResponseEntity <RestaurantRegion>(region, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/regions/r={rest_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantRegion> createRegion(@RequestBody RestaurantRegion region, @PathVariable("rest_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        region.setRestaurant(restaurant);
        RestaurantRegion created = regionService.create(region);
        return new ResponseEntity <RestaurantRegion>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/regions/r={rest_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantRegion> updateRegion(@RequestBody RestaurantRegion region, @PathVariable("rest_id") Integer id) {
        Restaurant restaurant = restaurantService.findOne(id);
        region.setRestaurant(restaurant);
        RestaurantRegion updated = regionService.update(region);
        if (updated == null) {
            return new ResponseEntity <RestaurantRegion>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <RestaurantRegion>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/regions/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <RestaurantRegion> deleteRegion(@PathVariable("id") Integer id) {
        regionService.delete(id);
        return new ResponseEntity <RestaurantRegion>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/regions/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantRegion>> getRegionsByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <RestaurantRegion> restaurantRegions = regionService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <RestaurantRegion>>(restaurantRegions, HttpStatus.OK);
    }

}
