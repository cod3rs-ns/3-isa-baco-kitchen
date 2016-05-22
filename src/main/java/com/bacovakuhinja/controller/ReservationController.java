package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.aspects.SendMailAspect;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.ReservationGuest;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ReservationController {

    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static final String INVITED  = "invited";
    private static final String OWNER    = "owner";

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReservationGuestService reservationGuestService;

    @Autowired
    private UserService userService;


    @Authorization(value = "guest")
    @RequestMapping(
            value    = "/api/reservation",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Reservation> createReservation(final HttpServletRequest request, @RequestBody Reservation reservation) {

        Guest user = (Guest) request.getAttribute("loggedUser");

        Restaurant restaurant = restaurantService.findOne(reservation.getRestaurant().getRestaurantId());
        reservation.setRestaurant(restaurant);

        Reservation created = reservationService.create(reservation);

        ReservationGuest reservationGuest = new ReservationGuest();
        reservationGuest.setReservation(created);
        reservationGuest.setReservationGuest(user);
        reservationGuest.setStatus(OWNER);

        reservationGuestService.create(reservationGuest);

        return new ResponseEntity<Reservation>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value    = "/api/reservation/invite",
            method   = RequestMethod.HEAD
    )
    public void inviteFriend(@RequestParam(value="reservation") Integer reservationId, @RequestParam(value="friend") String email) {
        Reservation reservation = reservationService.findOne(reservationId);
        Guest friend = (Guest) userService.findOne(email);

        ReservationGuest reservationGuest = new ReservationGuest();
        reservationGuest.setReservation(reservation);
        reservationGuest.setReservationGuest(friend);
        reservationGuest.setStatus(INVITED);

        reservationGuestService.create(reservationGuest);

        // FIXME Better implementation.

        try {
            SendMailAspect.sendMail(email, "Poziv na rezervaciju", "Test");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
