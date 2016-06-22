package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.RestaurantRegionService;
import com.bacovakuhinja.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@RestController
public class RestaurantTableController {
    @Autowired
    private RestaurantTableService tableService;


    @Autowired
    private RestaurantRegionService regionService;

    @RequestMapping(
            value = "/api/tables",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantTable>> getTables() {
        Collection <RestaurantTable> tables = tableService.findAll();
        return new ResponseEntity <Collection <RestaurantTable>>(tables, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/tables/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantTable> getTable(@PathVariable("id") Integer id) {
        RestaurantTable table = tableService.findOne(id);
        return new ResponseEntity <RestaurantTable>(table, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/tables/r={reg_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantTable> createTable(@RequestBody RestaurantTable table, @PathVariable("reg_id") Integer id) {
        RestaurantRegion region = regionService.findOne(id);
        table.setRegion(region);
        RestaurantTable created = tableService.create(table);
        return new ResponseEntity <RestaurantTable>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/tables/r={reg_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RestaurantTable> updateTable(@RequestBody RestaurantTable table, @PathVariable("reg_id") Integer id) {
        RestaurantRegion region = regionService.findOne(id);
        table.setRegion(region);
        RestaurantTable updated = tableService.update(table);
        if (updated == null) {
            return new ResponseEntity <RestaurantTable>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <RestaurantTable>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/tables/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <RestaurantTable> deleteTable(@PathVariable("id") Integer id) {
        RestaurantTable rt = tableService.findOne(id);
        rt.setDeleted(true);
        tableService.update(rt);
        return new ResponseEntity <RestaurantTable>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/tables/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <RestaurantTable>> getTablesByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <RestaurantTable> restaurantTables = tableService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <RestaurantTable>>(restaurantTables, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/tables/next",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Integer> getNextId() {
        Integer nextId = tableService.getNextId();
        return new ResponseEntity <Integer>(nextId, HttpStatus.OK);
    }
}
