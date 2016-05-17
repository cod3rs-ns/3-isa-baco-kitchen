package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.DailySchedule;
import com.bacovakuhinja.service.DailyScheduleService;
import com.bacovakuhinja.service.EmployeeService;
import com.bacovakuhinja.service.RestaurantRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
public class DailyScheduleController {

    @Autowired
    private DailyScheduleService dailyScheduleService;

    @Autowired
    private RestaurantRegionService regionService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(
            value = "/api/daily_schedules",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <DailySchedule>> getDailySchedules() {
        Collection <DailySchedule> dailySchedules = dailyScheduleService.findAll();
        return new ResponseEntity <Collection <DailySchedule>>(dailySchedules, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/daily_schedules/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <DailySchedule> getDailySchedule(@PathVariable("id") Integer id) {
        DailySchedule dailySchedule = dailyScheduleService.findOne(id);
        return new ResponseEntity <DailySchedule>(dailySchedule, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/daily_schedules/r={rest_id}+e={emp_id}+r={reg_id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <DailySchedule> createDailySchedule(@RequestBody DailySchedule dailySchedule, @PathVariable("rest_id") Integer restaurantId, @PathVariable("emp_id") Integer employeeId,
                                                              @PathVariable("reg_id") Integer regionId) {
        dailySchedule.setRegion(regionService.findOne(regionId));
        dailySchedule.setEmployee(employeeService.findOne(employeeId));
        dailySchedule.setRestaurantId(restaurantId);
        DailySchedule created = dailyScheduleService.create(dailySchedule);
        return new ResponseEntity <DailySchedule>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/daily_schedules/r={rest_id}+e={emp_id}+r={reg_id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <DailySchedule> updateDailySchedule(@RequestBody DailySchedule dailySchedule, @PathVariable("rest_id") Integer restaurantId, @PathVariable("emp_id") Integer employeeId,
                                                              @PathVariable("reg_id") Integer regionId) {
        dailySchedule.setRegion(regionService.findOne(regionId));
        dailySchedule.setEmployee(employeeService.findOne(employeeId));
        dailySchedule.setRestaurantId(restaurantId);
        DailySchedule updated = dailyScheduleService.update(dailySchedule);
        if (updated == null) {
            return new ResponseEntity <DailySchedule>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <DailySchedule>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/daily_schedules/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <DailySchedule> deleteRegion(@PathVariable("id") Integer id) {
        dailyScheduleService.delete(id);
        return new ResponseEntity <DailySchedule>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/daily_schedules/rst={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <DailySchedule>> findByRestaurant(@PathVariable("rst_id") Integer restaurantId) {
        Collection <DailySchedule> DailySchedules = dailyScheduleService.findByRestaurant(restaurantId);
        return new ResponseEntity <Collection <DailySchedule>>(DailySchedules, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/daily_schedules/emp={emp_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <DailySchedule>> findByEmployee(@PathVariable("emp_id") Integer employeeId) {
        Collection <DailySchedule> DailySchedules = dailyScheduleService.findByEmployee(employeeId);
        return new ResponseEntity <Collection <DailySchedule>>(DailySchedules, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/daily_schedules/reg={reg_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <DailySchedule>> findByRegion(@PathVariable("reg_id") Integer regionId) {
        Collection <DailySchedule> DailySchedules = dailyScheduleService.findByRestaurantRegion(regionId);
        return new ResponseEntity <Collection <DailySchedule>>(DailySchedules, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/daily_schedules/day",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <DailySchedule>> findByDay(@RequestBody Date day) {
        Collection <DailySchedule> DailySchedules = dailyScheduleService.findByDay(day);
        return new ResponseEntity <Collection <DailySchedule>>(DailySchedules, HttpStatus.OK);
    }
}
