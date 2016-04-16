package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Cook;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.RestaurantManager;
import com.bacovakuhinja.model.SystemManager;
import com.bacovakuhinja.service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CookController {
    @Autowired
    private CookService cookService;

    @RequestMapping(
            value = "/api/cooks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Cook>> getCooks() {
        Collection <Cook> cooks = cookService.findAll();
        return new ResponseEntity <Collection <Cook>>(cooks, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/cooks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> getCook(@PathVariable("id") Integer id) {
        Cook cook = cookService.findOne(id);
        return new ResponseEntity <Cook>(cook, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/cook",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> createCook(@RequestBody Cook cook) {
        Cook created = cookService.create(cook);
        return new ResponseEntity<Cook>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/cook",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> updateRestaurantManager(@RequestBody Cook cook) {
        Cook updatedCook = cookService.update(cook);
        if (updatedCook == null) {
            return new ResponseEntity <Cook>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Cook>(updatedCook, HttpStatus.OK);
    }
}
