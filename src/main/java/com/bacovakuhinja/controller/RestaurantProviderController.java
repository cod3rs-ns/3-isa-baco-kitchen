package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.RestaurantProvider;
import com.bacovakuhinja.service.RestaurantProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class RestaurantProviderController {

    @Autowired
    private RestaurantProviderService providerService;

    @RequestMapping(
            value = "/api/providers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RestaurantProvider>> getProviders(){
        Collection<RestaurantProvider> providers = providerService.findAll();
        return new ResponseEntity<Collection<RestaurantProvider>>(providers, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/providers",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantProvider> createEvent(@RequestBody RestaurantProvider provider) {
        RestaurantProvider created = providerService.create(provider);
        return new ResponseEntity<RestaurantProvider>(created, HttpStatus.CREATED);
    }
}
