package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.FriendshipService;
import com.bacovakuhinja.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    FriendshipService friendshipService;

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/requests",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getRequests(final HttpServletRequest request) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        Collection<User> requests = friendshipService.getFriendshipsRequestByGuestID(user.getGuestId());

        return new ResponseEntity<Collection<User>>(requests, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/friends",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getFriends(final HttpServletRequest request) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        Collection<User> friends = friendshipService.getFriendsByGuestID(user.getGuestId());

        return new ResponseEntity<Collection<User>>(friends, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/accept-friend/{senderId}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> acceptFriend(final HttpServletRequest request, @PathVariable Integer senderId) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        friendshipService.acceptRequest(senderId, user.getGuestId());
        System.out.println("Accepted called");
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/reject-friend/{senderId}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> rejectFriend(final HttpServletRequest request, @PathVariable Integer senderId) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        friendshipService.rejectRequest(senderId, user.getGuestId());
        System.out.println("Rejected called");
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

}
