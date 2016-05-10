package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.DayScheduleService;
import com.bacovakuhinja.service.EmployeeService;
import com.bacovakuhinja.service.WaiterService;
import com.bacovakuhinja.service.impl.WaiterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class WaiterController {

    @Autowired
    private WaiterService waiterService;

    @Autowired
    private DayScheduleService dayScheduleService;

    @RequestMapping(
            value = "/api/waiters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Waiter>> getWaiters() {
        Collection <Waiter> waiters =  waiterService.findAll();
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


    @Authorization(value = "waiter")
    @RequestMapping(
            value = "/api/waiter",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> getLoggedInCook(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());
        return new ResponseEntity <Waiter>(waiter, HttpStatus.OK);
    }


    @Authorization(value = "waiter")
    @RequestMapping(
            value = "/api/waiter/tables",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ArrayList<RestaurantTable>> getTables(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        //TODO get real tables from database
        RestaurantTable t = new RestaurantTable();
        t.setTableId(1);

        ArrayList<RestaurantTable> tables = new ArrayList<RestaurantTable>();
        tables.add(t);
        return new ResponseEntity <ArrayList<RestaurantTable>>(tables, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/waiter",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Waiter> createWaiter(@RequestBody Waiter waiter) {
        waiter.setPassword("generated_password");
        waiter.setVerified("not_verified");
        Waiter created = waiterService.create(waiter);
        return new ResponseEntity<Waiter>(created, HttpStatus.CREATED);
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
