package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.ShiftTemplate;
import com.bacovakuhinja.service.RestaurantService;
import com.bacovakuhinja.service.ShiftTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ShiftTemplateController {

    @Autowired
    private ShiftTemplateService shiftTemplateService;

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            value = "/api/shifts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <ShiftTemplate>> getShiftTemplates() {
        Collection <ShiftTemplate> shiftTemplates = shiftTemplateService.findAll();
        return new ResponseEntity <Collection <ShiftTemplate>>(shiftTemplates, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/shifts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ShiftTemplate> getShiftTemplate(@PathVariable("id") Integer id) {
        ShiftTemplate shiftTemplate = shiftTemplateService.findOne(id);
        return new ResponseEntity <ShiftTemplate>(shiftTemplate, HttpStatus.OK);
    }


    @RequestMapping(value = "/api/shifts",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ShiftTemplate> createShiftTemplate(@RequestBody ShiftTemplate shiftTemplate) {
        ShiftTemplate created = shiftTemplateService.create(shiftTemplate);
        return new ResponseEntity <ShiftTemplate>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/shifts",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ShiftTemplate> updateShiftTemplate(@RequestBody ShiftTemplate shiftTemplate) {
        ShiftTemplate updated = shiftTemplateService.update(shiftTemplate);
        if (updated == null) {
            return new ResponseEntity <ShiftTemplate>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <ShiftTemplate>(updated, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/shifts/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <ShiftTemplate> deleteShiftTemplate(@PathVariable("id") Integer id) {
        shiftTemplateService.delete(id);
        return new ResponseEntity <ShiftTemplate>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/shifts/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <ShiftTemplate>> findShiftTemplatesByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <ShiftTemplate> shiftTemplates = shiftTemplateService.findByRestaurant(id);
        return new ResponseEntity <Collection <ShiftTemplate>>(shiftTemplates, HttpStatus.OK);
    }
}