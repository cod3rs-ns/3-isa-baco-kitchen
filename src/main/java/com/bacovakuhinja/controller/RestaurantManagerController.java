package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.RestaurantManager;
import com.bacovakuhinja.model.Sha256;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.RestaurantManagerService;
import com.bacovakuhinja.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
public class RestaurantManagerController {
    @Autowired
    private RestaurantManagerService rmService;

    @Autowired
    private RestaurantService rService;

    @RequestMapping(
            value = "/api/rmanagers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantManager>> getRestaurantManagers() {
        Collection <RestaurantManager> managers = rmService.findAll();
        return new ResponseEntity <Collection <RestaurantManager>>(managers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/rmanagers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> getRestaurantManager(@PathVariable("id") Integer id) {
        RestaurantManager manager = rmService.findOne(id);
        return new ResponseEntity <RestaurantManager>(manager, HttpStatus.OK);
    }

    @Authorization(role = "restaurant_manager")
    @RequestMapping(
            value = "/api/rmanager",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> getLoggedInRestaurantManager(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        RestaurantManager manager = rmService.findOne(user.getUserId());
        return new ResponseEntity <RestaurantManager>(manager, HttpStatus.OK);
    }

    @SendEmail
    @RequestMapping(value = "/api/rmanagers/{restaurant_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> createRestaurantManager(@RequestBody RestaurantManager manager, @PathVariable("restaurant_id") Integer r_id) {
        Restaurant restaurant = rService.findOne(r_id);
        manager.setRestaurant(restaurant);
        // TODO generate password and verified
        manager.setPassword(Sha256.getSha256("generated_password"));
        manager.setVerified("not_verified");
        RestaurantManager createdManager = rmService.create(manager);
        return new ResponseEntity <RestaurantManager>(createdManager, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/rmanagers",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> updateRestaurantManager(@RequestBody RestaurantManager manager) {
        manager.setPassword(rmService.findOne(manager.getUserId()).getPassword());
        RestaurantManager updatedManager = rmService.update(manager);
        if (updatedManager == null) {
            return new ResponseEntity <RestaurantManager>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <RestaurantManager>(updatedManager, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/rmanagers/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <RestaurantManager> deleteRestaurantManager(@PathVariable("id") Integer id) {
        rmService.delete(id);
        return new ResponseEntity <RestaurantManager>(HttpStatus.NO_CONTENT);
    }
}
