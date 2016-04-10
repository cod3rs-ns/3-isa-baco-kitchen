package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

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

    @RequestMapping(
            value    = "/api/user/{email:.+}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> getUser(@PathVariable String email) {
        for (User user : userService.findAll()) {
            if (user.getEmail().equals(email)) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }

        return null;
    }

    @RequestMapping(
            value    = "/api/authenticate",
            method   = RequestMethod.POST
    )
    public LoginResponse authenticate(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {

        for (User user : userService.findAll()) {
            if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                return new LoginResponse(Jwts.builder().setSubject(username)
                        .claim("role", "guest").setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
            }
        }

        return null;
    }


    private static class LoginResponse {
        public String token;
        public LoginResponse(final String token) {
            this.token = token;
        }
    }

}
