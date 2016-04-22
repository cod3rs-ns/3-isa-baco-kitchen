package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.FriendshipService;
import com.bacovakuhinja.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    FriendshipService friendshipService;

    @RequestMapping (
            value    = "/api/guest/requests",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getRequests() {
        System.out.println("Get Request");
        Collection<User> requests = friendshipService.getFriendshipsRequestByGuestID(2);

        for (User u : requests)
            System.out.println(u);

        return new ResponseEntity<Collection<User>>(requests, HttpStatus.OK);
    }
}
