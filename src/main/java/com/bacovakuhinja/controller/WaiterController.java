package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.RestaurantRegion;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.model.Waiter;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
public class WaiterController {

    @Autowired
    private WaiterService waiterService;

    @Autowired
    private DailyScheduleService dailyScheduleService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRegionService restaurantRegionService;

    @RequestMapping(
            value = "/api/waiters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Waiter>> getWaiters() {
        Collection <Waiter> waiters = waiterService.findAll();
        return new ResponseEntity <Collection <Waiter>>(waiters, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/waiters/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> getWaiter(@PathVariable("id") Integer id) {
        Waiter waiter = waiterService.findOne(id);
        return new ResponseEntity <Waiter>(waiter, HttpStatus.OK);
    }


    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/waiter",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> getLoggedInCook(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());
        return new ResponseEntity <Waiter>(waiter, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/waiter/tables/region={regionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantTable>> getTables(@PathVariable("regionId")Integer regionId) {
        RestaurantRegion region = restaurantRegionService.findOne(regionId);
        if(region == null)
            return new ResponseEntity <Collection <RestaurantTable>>(HttpStatus.NOT_FOUND);

        return new ResponseEntity <Collection <RestaurantTable>>(region.getTables(), HttpStatus.OK);
    }

    @SendEmail
    @RequestMapping(value = "/api/waiter",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> createWaiter(@RequestBody Waiter waiter) {
        waiter.setPassword("generated_password");
        waiter.setVerified("not_verified");
        Waiter created = waiterService.create(waiter);
        return new ResponseEntity <Waiter>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/waiter",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> updateWaiter(@RequestBody Waiter waiter) {
        Waiter updatedWaiter = waiterService.update(waiter);
        if (updatedWaiter == null) {
            return new ResponseEntity <Waiter>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Waiter>(updatedWaiter, HttpStatus.OK);
    }
}
