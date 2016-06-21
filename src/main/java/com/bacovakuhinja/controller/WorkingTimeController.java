package com.bacovakuhinja.controller;


import com.bacovakuhinja.model.WorkingTime;
import com.bacovakuhinja.service.RestaurantService;
import com.bacovakuhinja.service.WorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class WorkingTimeController {

    @Autowired
    private WorkingTimeService workingTimeService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/working_times",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <WorkingTime>> getWorkingTimes() {
        Collection <WorkingTime> workingTimes = workingTimeService.findAll();
        return new ResponseEntity <Collection <WorkingTime>>(workingTimes, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/working_times/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <WorkingTime> getWorkingTime(@PathVariable("id") Integer id) {
        WorkingTime workingTime = workingTimeService.findOne(id);
        return new ResponseEntity <WorkingTime>(workingTime, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/working_times",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <WorkingTime> createWorkingTime(@RequestBody WorkingTime workingTime) {
        WorkingTime created = workingTimeService.create(workingTime);
        return new ResponseEntity <WorkingTime>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/working_times",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <WorkingTime> updateWorkingTime(@RequestBody WorkingTime workingTime) {
        WorkingTime updated = workingTimeService.update(workingTime);
        if (updated == null) {
            return new ResponseEntity <WorkingTime>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <WorkingTime>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/working_times/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <WorkingTime> findByRestaurant(@PathVariable("rst_id") Integer id) {
        WorkingTime workingTime = workingTimeService.findByRestaurant(id);
        return new ResponseEntity <WorkingTime>(workingTime, HttpStatus.OK);
    }
}
