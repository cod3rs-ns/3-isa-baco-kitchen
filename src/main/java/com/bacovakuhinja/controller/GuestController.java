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
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(
            value    = "/api/guest/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request, @PathVariable Integer id) {
        Guest guest = guestService.getGuest(id);
        return new ResponseEntity<Guest>(guest, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/admin/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isAdmin(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        return new ResponseEntity<Boolean>(user.getGuestId() == id, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/friend/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        return new ResponseEntity<Boolean>(friendshipService.areWeFriends(user.getGuestId(), id), HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/friends/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getFriends(final HttpServletRequest request, @PathVariable Integer id) {
        if (id == -1) {
            Guest user = (Guest) request.getAttribute("loggedUser");
            id = user.getGuestId();
        }
        
        Collection<User> friends = friendshipService.getFriendsByGuestID(id);

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
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
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
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/add-friend/{id}",
            method   = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        System.out.println("add friend");
        friendshipService.addFriend(user.getGuestId(), id);
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping (
            value    = "/api/guest/remove-friend/{id}",
            method   = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> removeFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        System.out.println("remove friend");
        friendshipService.removeFriend(user.getGuestId(), id);
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @RequestMapping (
            value    = "api/guest/users",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> queryResults(@RequestParam(value="query") String query) {
        Collection<User> result = guestService.getUsers(query.toLowerCase());

        return new ResponseEntity<Collection<User>>(result, HttpStatus.OK);}

}
