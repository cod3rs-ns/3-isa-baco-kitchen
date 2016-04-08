package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value    = "/api/users",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getUsers() {
        Collection<User> users = userService.findAll();
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/users/{email:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> userExists(@PathVariable String email) {
        boolean exists = userService.alreadyExists(email);
        return new ResponseEntity<Boolean>(exists, HttpStatus.OK);
    }

}
