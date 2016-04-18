package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;

@RestController
public class UserController {

    private static final String SECRET_KEY = "VojislavSeselj";

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

    @Authorization(value = "guest")
    @RequestMapping(
            value    = "/api/user",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request) {

        User user = (User) request.getAttribute("loggedUser");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/authenticate",
            method   = RequestMethod.POST
    )
    public LoginResponse authenticate(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {

        for (User user : userService.findAll()) {
            if (user.getEmail().equals(username) && user.getPassword().equals(password) && user.getVerified().equals("verified")) {
                return new LoginResponse(Jwts.builder().setSubject(username)
                        .claim("user", username).setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact());
            }
        }

        return null;
    }

    @SendEmail
    @RequestMapping(
            value    = "api/user/register",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> register(@RequestBody User user) {

        System.out.println(user.toString());
        User created = userService.create(user);
        return new ResponseEntity<User>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value    = "api/user/update",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> update(@RequestBody User user) {
        System.out.println("Updating... ");
        System.out.println(user.toString());
        User updated = userService.update(user);
        return new ResponseEntity<User>(updated, HttpStatus.OK);
    }

    private static class LoginResponse {
        public String token;
        public LoginResponse(final String token) {
            this.token = token;
        }
    }

}
