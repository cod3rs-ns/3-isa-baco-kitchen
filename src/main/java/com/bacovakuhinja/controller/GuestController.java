package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    UserService userService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationGuestService reservationGuestService;

    @Autowired
    ReviewService reviewService;

    @Authorization(role = "guest")
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

    @Authorization(role = "guest")
    @RequestMapping(
            value    = "/api/guest/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request, @PathVariable Integer id) {
        Guest guest = guestService.getGuest(id);
        return new ResponseEntity<Guest>(guest, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/admin/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isAdmin(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        return new ResponseEntity<Boolean>(user.getGuestId() == id, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/friend/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        return new ResponseEntity<Boolean>(friendshipService.areWeFriends(user.getGuestId(), id), HttpStatus.OK);
    }

    @Authorization(role = "guest")
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

    @Authorization(role = "guest")
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

    @Authorization(role = "guest")
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

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/add-friend/{id}",
            method   = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
        friendshipService.addFriend(user.getGuestId(), id);
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/remove-friend/{id}",
            method   = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> removeFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");
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
        return new ResponseEntity<Collection<User>>(result, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "api/guest/reservations/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Reservation>> getReservations(@PathVariable Integer id) {
        Collection<Reservation> result = reservationService.findByOwnerId(id);
        return new ResponseEntity<Collection<Reservation>>(result, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "api/guest/visits/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<ReservationHelper>> getVisits(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");

        Collection<ReservationHelper> result = new ArrayList<ReservationHelper>();
        Collection<Reservation> reservations = reservationService.findVisitsByOwnerId(id);

        for (Reservation reservation : reservations) {
            Review review = reviewService.getReviewByReservation(reservation.getReservationId(), id);

            // FIXME Restaurant Image From DataBase
            reservation.setRestaurant(reservation.getRestaurant());
            result.add(new ReservationHelper(reservation, review, reservation.getRestaurant().getName(),
                    "https://www.wien.info/media/images/restaurant-konstantin-filippou.jpg/image_leading_article_teaser"));
        }

        return new ResponseEntity<Collection<ReservationHelper>>(result, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "api/guest/invitations/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<ReservationHelper>> getInvitations(final HttpServletRequest request, @PathVariable Integer id) {

        Collection<ReservationHelper> result = new ArrayList<ReservationHelper>();
        Collection<Reservation> invitations = reservationService.findInvitationsByOwnerId(id);

        for (Reservation reservation : invitations) {
            // FIXME Restaurant Image From DataBase
            reservation.setRestaurant(reservation.getRestaurant());

            ReservationHelper helper = new ReservationHelper(reservation, null, reservation.getRestaurant().getName(),
                    "https://www.wien.info/media/images/restaurant-konstantin-filippou.jpg/image_leading_article_teaser");

            // FIXME Find Reservation Owner's Name and Lastname
            helper.setInviter("Ime i prezme");
            result.add(helper);
        }

        return new ResponseEntity<Collection<ReservationHelper>>(result, HttpStatus.OK);
    }

    // FIXME @DMG
    @RequestMapping (
            value    = "api/guest/logged",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isGuestLoggedIn(final HttpServletRequest request) {
        final String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }

        // Authorization token
        final String token = auth.substring(7);

        final Claims claims = Jwts.parser().setSigningKey("VojislavSeselj")
                .parseClaimsJws(token).getBody();

        final String email = claims.get("user").toString();


        User guest = null;
        // FIXME Maybe change...
        for (User user : userService.findAll()) {
            if (user.getEmail().equals(email) && user.getType().equals("guest")) {
                guest = user;
                break;
            }
        }

        System.out.println(guest);

        if (guest != null) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/accept-invite/{id}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> acceptInvite(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");

        reservationGuestService.acceptInvitation(id, user.getGuestId());

        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping (
            value    = "/api/guest/decline-invite/{id}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> declineInvite(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute("loggedUser");

        reservationGuestService.declineInvitation(id, user.getGuestId());

        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

}