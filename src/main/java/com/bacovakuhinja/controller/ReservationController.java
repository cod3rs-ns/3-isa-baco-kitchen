package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.aspects.SendMailAspect;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class ReservationController {

    private static final String INVITE_TITLE = "Poziv na rezervaciju";

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

    @Autowired
    private ClientOrderService clientOrderService;

    @RequestMapping(
            value    = "/api/reservations/{restaurant_id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Reservation>> findReservationsByRestaurantId(@PathVariable("restaurant_id") Integer restaurantId) {
        Collection<Reservation> reservations = reservationService.findByRestaurantId(restaurantId);
        return new ResponseEntity<Collection<Reservation>>(reservations, HttpStatus.CREATED);
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

        // Find All Restaurant's tables
        Collection<RestaurantTable> freeTables = tableService.findAllByRestaurant(restaurantId);
        // Find All Reservations in  Restaurant by similar date and time
        Collection<Reservation> similarReservations = reservationService.findByRestaurantIdAndTime(restaurantId, new Date(Long.parseLong(datetime)), length);

        // For each reservation get reserved tables and erase them from free tables
        Collection<ReservationTable> reservedTables;
        for (Reservation reservation : similarReservations) {
            reservedTables = reservationTableService.findAllByReservationId(reservation.getReservationId());

            for (ReservationTable reservedTable : reservedTables) {
                freeTables.remove(reservedTable.getTable());
            }
        }

        return new ResponseEntity<Collection<RestaurantTable>>(freeTables, HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/reservation/invited/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReservationInvite> getInviteParams(@PathVariable("id") Integer id) {

        Reservation reservation = reservationService.findOne(id);
        Integer restaurantId = reservation.getRestaurant().getRestaurantId();

        Integer alreadyOrdered = clientOrderService.findByReservation(id);
        Collection<ReservationTable> tables = reservationTableService.findAllByReservationId(reservation.getReservationId());

        Integer tableId = null;
        ReservationTable rt;
        for (Iterator<ReservationTable> iterator = tables.iterator(); iterator.hasNext();) {
            rt = iterator.next();

            Integer capacity = rt.getTable().getPositions();
            alreadyOrdered = alreadyOrdered - capacity;

            if (alreadyOrdered < 0) {
                tableId = rt.getTable().getTableId();
                break;
            }
        }

        return new ResponseEntity<ReservationInvite>(new ReservationInvite(reservation, tableId, restaurantId), HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value    = "/api/reservation",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createReservation(final HttpServletRequest request, @RequestBody ReservationCreator creator) {

        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        ArrayList<Integer> tables = creator.getTables();
        Reservation reservation = creator.getReservation();

        Restaurant restaurant = restaurantService.findOne(reservation.getRestaurant().getRestaurantId());
        reservation.setRestaurant(restaurant);

        // Create Reservation
        reservation = reservationService.create(reservation);

        if (reservation == null)
            return  new ResponseEntity<String>("{\"answer\": \"WRONG_RESERVATION\"}", HttpStatus.OK);

        // Create Reservation Owner
        ReservationGuest reservationGuest = new ReservationGuest(user, reservation, Constants.Reservation.OWNER);
        reservationGuestService.create(reservationGuest);

        // If there's no chosen tables
        if (0 == tables.size()) {
            reservationGuestService.delete(reservationGuest);
            reservationService.delete(reservation);
            return new ResponseEntity<String>("{ \"answer\": \"NO_TABLES\"}", HttpStatus.OK);
        }

        // Get free tables again, get similar reservations again and make free tables at this point
        Collection<RestaurantTable> freeTables = tableService.findAllByRestaurant(reservation.getRestaurant().getRestaurantId());
        Collection<Reservation> similarReservations = reservationService.findByRestaurantIdAndTime(reservation.getRestaurant().getRestaurantId(), reservation.getReservationDateTime(), reservation.getLength());

        Collection<ReservationTable> reservedTables;
        for (Reservation res : similarReservations) {
            reservedTables = reservationTableService.findAllByReservationId(res.getReservationId());

            for (ReservationTable reservedTable : reservedTables) {
                freeTables.remove(reservedTable.getTable());
            }
        }

        // For every table if we find it's not in free tables, remove reservation and return fail
        boolean p;
        for (Integer tableId : tables) {
            p = false;

            for (RestaurantTable tbl : freeTables) {
                if (tbl.getTableId() == tableId) {
                    p = true;
                    break;
                }
            }

            if (!p) {
                reservationGuestService.delete(reservationGuest);
                reservationService.delete(reservation);
                return new ResponseEntity<String>("{ \"answer\": \"WRONG_TABLES\"}", HttpStatus.OK);
            }
        }

        // Add Reservation Tables
        for (Integer tableId : tables) {
            ReservationTable table = new ReservationTable();
            table.setReservation(reservation);
            table.setTable(tableService.findOne(tableId));

            reservationTableService.save(table);
        }

        return new ResponseEntity<Reservation>(reservation, HttpStatus.CREATED);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value    = "/api/reservation/{id}",
            method   = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> cancelReservation(final HttpServletRequest request, @PathVariable("id") Integer id) {

        Guest owner = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);

        Reservation reservation = reservationService.findOne(id);

        if (!reservationGuestService.isOwner(id, owner.getGuestId()))
            return new ResponseEntity<Object>("{}", HttpStatus.UNAUTHORIZED);

        // Only delete REJECTED
        for (ReservationGuest rg : reservationGuestService.findAllByReservationAndStatus(id, Constants.Reservation.REJECTED)) {
            reservationGuestService.delete(rg);
        }

        // Delete ACCEPTED and INVITED and Send Email
        for (ReservationGuest rg : reservationGuestService.findAllByReservationAndStatus(id, Constants.Reservation.INVITED)) {
            reservationGuestService.delete(rg);
            // TODO Send Email
            System.out.println("Notify INVITED: " + rg.getReservationGuest().getEmail());
        }

        for (ReservationGuest rg : reservationGuestService.findAllByReservationAndStatus(id, Constants.Reservation.ACCEPTED)) {
            reservationGuestService.delete(rg);
            // TODO Send Email
            System.out.println("Notify ACCEPTED: " + rg.getReservationGuest().getEmail());
        }

        // Delete Reservation Owner
        ReservationGuest rg = reservationGuestService.findAllByReservationAndStatus(id, Constants.Reservation.OWNER).iterator().next();
        reservationGuestService.delete(rg);

        for (ClientOrder co : clientOrderService.findOrdersByReservation(id)) {
            clientOrderService.delete(co.getOrderId());
        }

        for (ReservationTable rt : reservationTableService.findAllByReservationId(id)) {
            reservationTableService.delete(rt);
        }

        reservationService.delete(reservation);

        return new ResponseEntity<Object>("{}", HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value    = "/api/reservation/invite",
            method   = RequestMethod.HEAD
    )
    public void inviteFriend(final HttpServletRequest request, @RequestParam(value="reservation") Integer reservationId, @RequestParam(value="friend") String email) {
        Guest me = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);

        Reservation reservation = reservationService.findOne(reservationId);
        Guest friend = (Guest) userService.findOne(email);

        ReservationGuest reservationGuest = new ReservationGuest(friend, reservation, Constants.Reservation.INVITED);
        reservationGuestService.create(reservationGuest);

        // FIXME Better implementation.
        String html = getInviteBodyHTML(me.getFirstName() + " " + me.getLastName(),
                reservationId.toString(),
                reservation.getRestaurant().getName(),
                reservation.getRestaurant().getAddress(),
                reservation.getReservationDateTime().getDate() + "",
                reservation.getReservationDateTime().getTime() + "");

        try {
            SendMailAspect.sendMail(email, INVITE_TITLE, html);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getInviteBodyHTML(String friend, String reservationId, String restaurantName, String restaurantAddress, String reservationDate, String reservationTime) {
        return "<html>\n" +
                "  <head>\n" +
                "    <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div style=\"text-align: center;\">\n" +
                "      <img src=\"http://s33.postimg.org/pq0lf02pb/logo.png\" style=\"height: 196px;\">\n" +
                "      <h2 style=\"font-family: Roboto Medium; color: #283593; font-size: 24pt;\"><b>BA&#x106;OVA KUHINJA</b></h2>\n" +
                "      <h4 style=\"font-family: Roboto Light; color: #3f51b5;\">POZIV NA REGISTRACIJU</h4>\n" +
                "    </div>\n" +
                "\n" +
                "    <p style=\"font-family: Roboto; color: #333; text-align: center;\">\n" +
                "      <div style=\"font-family: Roboto; color: #333; text-align: center;\">\n" +
                "        Va&#x161; prijatelj <b>" + friend + "</b> Vas je pozvao da mu se pridru&#x17E;ite na rezervaciji.\n" +
                "      </div>\n" +
                "      <div style=\"font-family: Roboto; color: #333; text-align: center; margin-top: 24;\"> \n" +
                "        <a href=\"http://localhost:8091/index.html#/invite/" + reservationId +  "\" \n" +
                "          style=\"background-color: #43A047; padding: 12 24; border-radius: 4px; color: #fff; font-size: 18pt; text-decoration: none;\">\n" +
                "            Pogledaj detalje\n" +
                "        </a>\n" +
                "      </div>\n" +
                "      <div style=\"font-family: Roboto; color: #333; text-align: center; margin-top: 24; margin-bottom: 12;\">\n" +
                "        Tu mo&#x17E;ete prihvatiti/odbiti rezervaciju ili napraviti Va&#x161;u poru&#x17E;binu da bude spremna za Vas kada do&#x111;ete. \n" +
                "      </div>\n" +
                "    </p>\n" +
                "    \n" +
                "    <div style=\"background-color: #eee; padding: 24 12; margin 24 12; font-family: Roboto; color: #333;\">\n" +
                "      <div>\n" +
                "        <span>\n" +
                "          <i class=\"material-icons\" style=\"font-size: 24px;\">local_dining</i>\n" +
                "        </span>\n" +
                "        <span style=\"font-size: 24px; padding-left: 24px;\">\n" +
                "          " + restaurantName + "\n" +
                "        </span>\n" +
                "      </div>\n" +
                "      <div>\n" +
                "        <span>\n" +
                "          <i class=\"material-icons\" style=\"font-size: 24px;\">location_on</i>\n" +
                "        </span>\n" +
                "        <span style=\"font-size: 24px; padding-left: 24px;\">\n" +
                "         " + restaurantAddress + "\n" +
                "        </span>\n" +
                "      </div>\n" +
                "      <div>\n" +
                "        <span>\n" +
                "          <i class=\"material-icons\" style=\"font-size: 24px;\">date_range</i>\n" +
                "        </span>\n" +
                "        <span style=\"font-size: 24px; padding-left: 24px;\">\n" +
                "          " + reservationDate + "\n" +
                "        </span>\n" +
                "      </div>\n" +
                "      <div>\n" +
                "        <span>\n" +
                "          <i class=\"material-icons\" style=\"font-size: 24px;\">alarm</i>\n" +
                "        </span>\n" +
                "        <span style=\"font-size: 24px; padding-left: 24px;\">\n" +
                "          " + reservationTime + "\n" +
                "        </span>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }

}
