package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.SendEmail;
import com.bacovakuhinja.aspects.SendMailAspect;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.service.GuestService;
import com.bacovakuhinja.service.ReservationService;
import com.bacovakuhinja.service.RestaurantService;
import com.bacovakuhinja.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @RequestMapping(
            value    = "/api/reservation",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        System.out.println("Creating reservation...");
        System.out.println(reservation.getRestaurant().getName());
        System.out.println(reservation.getReservationDateTime());
        System.out.println(reservation.getLength());

        Restaurant restaurant = restaurantService.findOne(reservation.getRestaurant().getRestaurantId());
        reservation.setRestaurant(restaurant);

        Reservation created = reservationService.create(reservation);

        return new ResponseEntity<Reservation>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value    = "/api/reservation/invite",
            method   = RequestMethod.HEAD
    )
    public void inviteFriend(@RequestParam(value="reservation") Integer reservationId, @RequestParam(value="friend") String email) {
        Reservation reservation = reservationService.findOne(reservationId);
        Guest friend = (Guest) userService.findOne(email);

        // FIXME Better implementation.

        try {
            SendMailAspect.sendMail(email, "Poziv na rezervaciju", "Test");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
