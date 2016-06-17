package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.Bartender;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.BartenderService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
public class BartenderController {

    @Autowired
    private BartenderService bartenderService;

    @RequestMapping(
            value = "/api/bartenders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Bartender>> getBartenders() {
        Collection <Bartender> bartenders =  bartenderService.findAll();
        return new ResponseEntity <Collection <Bartender>>(bartenders, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/bartenders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Bartender> getBartender(@PathVariable("id") Integer id) {
        Bartender bartender = bartenderService.findOne(id);
        return new ResponseEntity <Bartender>(bartender, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/bartender",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Bartender> createBartender(@RequestBody Bartender bartender) {
        bartender.setPassword("generated_password");
        bartender.setVerified(Constants.Registration.STATUS_NOT_VERIFIED);
        Bartender created = bartenderService.create(bartender);
        return new ResponseEntity<Bartender>(created, HttpStatus.CREATED);
    }

    @Authorization(role = Constants.UserRoles.BARTENDER)
    @RequestMapping(
            value = "/api/bartender",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Bartender> getLoggedInBartender(final HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        Bartender bartender = bartenderService.findOne(user.getUserId());
        return new ResponseEntity <Bartender>(bartender, HttpStatus.OK);
    }

    @SendEmail
    @RequestMapping(
            value = "/api/bartender",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Bartender> updateBartender(@RequestBody Bartender bartender) {
        Bartender updatedBartender = bartenderService.update(bartender);
        if (updatedBartender == null) {
            return new ResponseEntity <Bartender>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <Bartender>(updatedBartender, HttpStatus.OK);
    }

}
