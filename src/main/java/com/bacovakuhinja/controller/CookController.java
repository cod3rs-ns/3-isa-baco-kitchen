package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.CookService;
import com.bacovakuhinja.utility.Constants;
import com.bacovakuhinja.utility.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Authorization(role = Constants.UserRoles.COOK)
    @RequestMapping(
            value = "/api/cook",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> getLoggedInCook(final HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        Cook cook = cookService.findOne(user.getUserId());
        return new ResponseEntity <Cook>(cook, HttpStatus.OK);
    }

    @SendEmail
    @RequestMapping(value = "/api/cook",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> createCook(@RequestBody Cook cook) {
        String pass = PasswordHelper.randomPassword();
        cook.setPassword(pass);
        cook.setLogged(false);
        cook.setVerified(Constants.Registration.STATUS_NOT_VERIFIED);
        Cook created = cookService.create(cook);
        return new ResponseEntity<Cook>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/cook",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Cook> updateCook(@RequestBody Cook cook) {
        Cook updatedCook = cookService.update(cook);
        if (updatedCook == null) {
            return new ResponseEntity <Cook>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Cook>(updatedCook, HttpStatus.OK);
    }
}
