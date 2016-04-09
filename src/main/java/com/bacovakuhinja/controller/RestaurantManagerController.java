package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.RestaurantManager;
import com.bacovakuhinja.service.RestaurantManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class RestaurantManagerController {
    @Autowired
    private RestaurantManagerService rmService;

    @RequestMapping(
            value = "/api/rmanagers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantManager>> getRestaurantMangers() {
        Collection <RestaurantManager> managers = rmService.findAll();
        return new ResponseEntity <Collection <RestaurantManager>>(managers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/rmanager/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> getRestaurantManagers(@PathVariable("id") Integer id) {
        RestaurantManager manager = rmService.findOne(id);
        return new ResponseEntity <RestaurantManager>(manager, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/rmanagers",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> createRestaurantManager(@RequestBody RestaurantManager manager) {
        RestaurantManager createdManager = rmService.create(manager);
        return new ResponseEntity <RestaurantManager>(createdManager, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/rmanagers",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantManager> updateRestaurantManager(@RequestBody RestaurantManager manager) {
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
