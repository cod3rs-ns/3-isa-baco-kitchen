package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.aspects.SendMailAspect;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@RestController
public class ReservationController {

    private static final String INVITED  = "invited";
    private static final String OWNER    = "owner";

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ReservationGuestService reservationGuestService;

    @Autowired
    private ReservationTableService reservationTableService;

    @Autowired
    private RestaurantTableService tableService;

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
            value    = "/api/reservation/free",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<RestaurantTable>> getFreeTables(
            @RequestParam(value="id") Integer restaurantId,
            @RequestParam(value="dt") String datetime,
            @RequestParam(value="len") Integer length) {

        Collection<RestaurantTable> freeTables = tableService.findAllByRestaurant(restaurantId);
        Collection<Reservation> similarReservations = reservationService.findByRestaurantIdAndTime(restaurantId, new Date(Long.parseLong(datetime)), length);

        for (Reservation reservation : similarReservations) {
            Collection<ReservationTable> reservedTables = reservationTableService.findAllByReservationId(reservation.getReservationId());

            for (ReservationTable reservedTable : reservedTables) {
                freeTables.remove(reservedTable.getTable());
            }
        }

        return new ResponseEntity<Collection<RestaurantTable>>(freeTables, HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/reservation/tables",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void addTablesToReservation(@RequestParam(value="reservation") Integer reservationId, @RequestBody ArrayList<Integer> tables) {
        Reservation reservation = reservationService.findOne(reservationId);

        for (Integer tableId : tables) {
            ReservationTable table = new ReservationTable();
            table.setReservation(reservation);
            table.setTable(tableService.findOne(tableId));

            reservationTableService.save(table);
        }
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
