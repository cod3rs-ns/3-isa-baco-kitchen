package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.utility.PasswordHelper;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.GuestService;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.utility.Constants;
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

    @Autowired
    private UserService userService;

    @Autowired
    private GuestService guestService;

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

    // FIXME @Baco, Fix this...
    @Authorization()
    @RequestMapping(
            value    = "/api/users/oldPassword/{pass}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> passMatch(final HttpServletRequest request, @PathVariable String pass) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        String hashed = PasswordHelper.getSha256(pass);
        boolean matched = user.getPassword().equals(hashed);
        return new ResponseEntity<Boolean>(matched, HttpStatus.OK);
    }

    @Authorization()
    @RequestMapping(
            value    = "/api/user",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request) {
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // FIXME @Baco, Fix this too... :)
    @Authorization()
    @RequestMapping(
            value = "/api/users/pass",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> changePassword(final HttpServletRequest request, @RequestBody String pass){
        User user = (User) request.getAttribute(Constants.Authorization.LOGGED_USER);
        User current = userService.findOne(user.getEmail());
        current.setPassword(PasswordHelper.getSha256(pass));
        if(!current.isLogged()){
            current.setLogged(true);
        }
        userService.update(current);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    // FIXME @Baco, Fix this too... :)
    @Authorization()
    @RequestMapping(
            value = "/api/users/passChanged/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isPasswordChanged(final HttpServletRequest request){
        User user = (User)request.getAttribute(Constants.Authorization.LOGGED_USER);
        User current = userService.findOne(user.getEmail());
        boolean ret = true;
        if(current != null)
            ret = current.isLogged();
        return new ResponseEntity<Boolean>(ret, HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/authenticate",
            method   = RequestMethod.POST
    )
    public LoginResponse authenticate(@RequestParam(value="email") String email, @RequestParam(value="password") String password) {
        password = PasswordHelper.getSha256(password);
        User user = userService.findOneByEmailAndPassword(email, password);

        if (user != null && user.getVerified().equals(Constants.Registration.STATUS_VERIFIED)) {
            return new LoginResponse(Jwts.builder().setSubject(email)
                    .claim(Constants.Authorization.CLAIMS_BODY, email).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, Constants.Authorization.SECRET_KEY).compact());
        }

        return null;
    }

    @SendEmail()
    @RequestMapping(
            value    = "api/user/register",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> register(@RequestBody Guest guest) {
        guest.setPassword(PasswordHelper.getSha256(guest.getPassword()));
        guest.setLogged(true);
        User created = guestService.create(guest);
        return new ResponseEntity<User>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value    = "api/user/update",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> update(@RequestBody User user) {
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
