package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.SystemManager;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.SystemManagerService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        Collection <SystemManager> smanagers = systemManagerService.findAll();
        return new ResponseEntity <Collection <SystemManager>>(smanagers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/smanagers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <SystemManager> getProvider(@PathVariable("id") Integer id) {
        SystemManager provider = systemManagerService.findOne(id);
        return new ResponseEntity <SystemManager>(provider, HttpStatus.OK);
    }


    @Authorization(role = Constants.UserRoles.SYSTEM_MANAGER)
    @RequestMapping(
            value = "/api/smanager",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <SystemManager> getLoggedInSystemManager(final HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        SystemManager sm = systemManagerService.findOne(user.getUserId());
        return new ResponseEntity <SystemManager>(sm, HttpStatus.OK);
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
