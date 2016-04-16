package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.SystemManager;
import com.bacovakuhinja.service.SystemManagerService;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RestController
public class SystemManagerController {

    @Autowired
    private SystemManagerService systemManagerService;


    @RequestMapping(
            value = "/api/smanagers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <SystemManager>> getManagers() {
        Collection <SystemManager> providers = systemManagerService.findAll();
        return new ResponseEntity <Collection <SystemManager>>(providers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/smanagers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <SystemManager> getProvider(@PathVariable("id") Integer id) {
        SystemManager provider = systemManagerService.findOne(id);
        return new ResponseEntity <SystemManager>(provider, HttpStatus.OK);
    }



    @RequestMapping(
            value = "/api/system_manager/{id}/restaurants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Restaurant>> getRestaurants(@PathVariable("id") Integer id) {
        SystemManager manager = systemManagerService.findOne(id);
        Set<Restaurant> restaurants = manager.getRestaurants();
        return new ResponseEntity <Collection<Restaurant>>(Collections.unmodifiableCollection(restaurants), HttpStatus.OK);
    }
}
