package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Registration;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.service.VerificationTokenService;
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

    @RequestMapping(
            value    = "/api/user",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request) {

        final String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        // Authorization token
        final String token = auth.substring(7);

        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();

        final String email = claims.get("user").toString();


        for (User user : userService.findAll()) {
            if (user.getEmail().equals(email)) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }

        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
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

    @Registration
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

    private static class LoginResponse {
        public String token;
        public LoginResponse(final String token) {
            this.token = token;
        }
    }

}
