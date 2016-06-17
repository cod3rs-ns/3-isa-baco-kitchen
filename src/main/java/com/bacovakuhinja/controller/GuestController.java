package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import com.bacovakuhinja.utility.Constants;
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
    ClientOrderService clientOrderService;

    @Autowired
    ReviewService reviewService;

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/requests",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getRequests(final HttpServletRequest request) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        Collection<User> requests = friendshipService.getFriendshipsRequestByGuestID(user.getGuestId());

        return new ResponseEntity<Collection<User>>(requests, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value    = "/api/guest/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUser(final HttpServletRequest request, @PathVariable Integer id) {
        Guest guest = guestService.getGuest(id);
        return new ResponseEntity<Guest>(guest, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/admin/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isAdmin(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        return new ResponseEntity<Boolean>(user.getGuestId() == id, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/friend/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> isFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        return new ResponseEntity<Boolean>(friendshipService.areWeFriends(user.getGuestId(), id), HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/friends/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getFriends(final HttpServletRequest request, @PathVariable Integer id) {

        if (id == -1) {
            Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
            id = user.getGuestId();
        }
        
        Collection<User> friends = friendshipService.getFriendsByGuestID(id);
        return new ResponseEntity<Collection<User>>(friends, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/accept-friend/{senderId}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> acceptFriend(final HttpServletRequest request, @PathVariable Integer senderId) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        friendshipService.acceptRequest(senderId, user.getGuestId());
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/reject-friend/{senderId}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> rejectFriend(final HttpServletRequest request, @PathVariable Integer senderId) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        friendshipService.rejectRequest(senderId, user.getGuestId());
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/add-friend/{id}",
            method   = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        friendshipService.addFriend(user.getGuestId(), id);
        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/remove-friend/{id}",
            method   = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> removeFriend(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
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

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "api/guest/reservations/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<ReservationHelper>> getReservations(final HttpServletRequest request, @PathVariable Integer id) {

        Collection<ReservationHelper> result = new ArrayList<ReservationHelper>();
        Collection<Reservation> reservations = reservationService.findByOwnerId(id);

        ClientOrder co;
        ReservationHelper helper;

        for (Reservation reservation : reservations) {
            co = clientOrderService.findByReservationAndUser(reservation.getReservationId(), id);
            // FIXME Restaurant Image From DataBase
            helper = new ReservationHelper(reservation, null, reservation.getRestaurant().getName(),
                    "https://www.wien.info/media/images/restaurant-konstantin-filippou.jpg/image_leading_article_teaser");

            helper.setOrder(co == null ? null : co.getOrderId());
            helper.setRestaurantId(reservation.getRestaurant().getRestaurantId());
            result.add(helper);
        }

        return new ResponseEntity<Collection<ReservationHelper>>(result, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "api/guest/visits/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<ReservationHelper>> getVisits(final HttpServletRequest request, @PathVariable Integer id) {

        Collection<ReservationHelper> result = new ArrayList<ReservationHelper>();
        Collection<Reservation> reservations = reservationService.findVisitsByOwnerId(id);

        Review review;

        for (Reservation reservation : reservations) {
            review = reviewService.getReviewByReservation(reservation.getReservationId(), id);

            // FIXME Restaurant Image From DataBase
            result.add(new ReservationHelper(reservation, review, reservation.getRestaurant().getName(),
                    "https://www.wien.info/media/images/restaurant-konstantin-filippou.jpg/image_leading_article_teaser"));
        }

        return new ResponseEntity<Collection<ReservationHelper>>(result, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "api/guest/invitations/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<ReservationHelper>> getInvitations(final HttpServletRequest request, @PathVariable Integer id) {

        Collection<ReservationHelper> result = new ArrayList<ReservationHelper>();
        Collection<Reservation> invitations = reservationService.findInvitationsByOwnerId(id);

        ReservationHelper helper;

        for (Reservation reservation : invitations) {
            // FIXME Restaurant Image From DataBase
            helper = new ReservationHelper(reservation, null, reservation.getRestaurant().getName(),
                    "https://www.wien.info/media/images/restaurant-konstantin-filippou.jpg/image_leading_article_teaser");

            helper.setInviter(reservationGuestService.getOwner(reservation.getReservationId()));
            helper.setRestaurantId(reservation.getRestaurant().getRestaurantId());
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

        final Claims claims = Jwts.parser().setSigningKey(Constants.Authorization.SECRET_KEY)
                .parseClaimsJws(token).getBody();

        final String email = claims.get(Constants.Authorization.CLAIMS_BODY).toString();


        User guest = null;
        // FIXME Maybe change...
        for (User user : userService.findAll()) {
            if (user.getEmail().equals(email) && user.getType().equals(Constants.UserRoles.GUEST)) {
                guest = user;
                break;
            }
        }

        if (guest != null) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/accept-invite/{id}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> acceptInvite(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        reservationGuestService.acceptInvitation(id, user.getGuestId());

        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping (
            value    = "/api/guest/decline-invite/{id}",
            method   = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> declineInvite(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        reservationGuestService.declineInvitation(id, user.getGuestId());

        return new ResponseEntity<Guest>(user, HttpStatus.OK);
    }

}