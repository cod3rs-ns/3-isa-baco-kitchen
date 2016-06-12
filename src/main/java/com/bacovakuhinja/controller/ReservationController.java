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

    @Authorization(role = "guest")
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
            value    = "/api/reservation/invited/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ReservationInvite> getInviteParams(@PathVariable("id") Integer id) {
        // FIXME Check if user is invited

        Reservation reservation = reservationService.findOne(id);
        Integer restaurantId = reservation.getRestaurant().getRestaurantId();

        // FIXME Give first free table, not first table
        Collection<ReservationTable> tables = reservationTableService.findAllByReservationId(reservation.getReservationId());
        Integer tableId = ((ReservationTable) tables.toArray()[0]).getTable().getTableId();

        return new ResponseEntity<ReservationInvite>(new ReservationInvite(reservation, tableId, restaurantId), HttpStatus.OK);
    }

    @RequestMapping(
            value    = "/api/reservation/tables",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> addTablesToReservation(@RequestParam(value="reservation") Integer reservationId, @RequestBody ArrayList<Integer> tables) {
        if (0 == tables.size())
            return new ResponseEntity<String>("{ \"answer\": \"NO_TABLES\"}", HttpStatus.OK);

        Reservation reservation = reservationService.findOne(reservationId);

        Collection<RestaurantTable> freeTables = tableService.findAllByRestaurant(reservation.getRestaurant().getRestaurantId());
        Collection<Reservation> similarReservations = reservationService.findByRestaurantIdAndTime(reservation.getRestaurant().getRestaurantId(), reservation.getReservationDateTime(), reservation.getLength());

        for (Reservation res : similarReservations) {
            Collection<ReservationTable> reservedTables = reservationTableService.findAllByReservationId(res.getReservationId());

            for (ReservationTable reservedTable : reservedTables) {
                freeTables.remove(reservedTable.getTable());
            }
        }

        boolean p;
        for (Integer tableId : tables) {
            p = false;

            for (RestaurantTable tbl : freeTables) {
                if (tbl.getTableId() == tableId) {
                    p = true;
                    break;
                }
            }

            if (!p) return new ResponseEntity<String>("{ \"answer\": \"WRONG_TABLES\"}", HttpStatus.OK);
        }

        for (Integer tableId : tables) {
            ReservationTable table = new ReservationTable();
            table.setReservation(reservation);
            table.setTable(tableService.findOne(tableId));

            reservationTableService.save(table);
        }

        return new ResponseEntity<String>("{ \"answer\": \"RESERVATION_OK\"}", HttpStatus.OK);
    }

    @Authorization(role = "guest")
    @RequestMapping(
            value    = "/api/reservation/invite",
            method   = RequestMethod.HEAD
    )
    public void inviteFriend(final HttpServletRequest request, @RequestParam(value="reservation") Integer reservationId, @RequestParam(value="friend") String email) {
        Guest me = (Guest) request.getAttribute("loggedUser");

        Reservation reservation = reservationService.findOne(reservationId);
        Guest friend = (Guest) userService.findOne(email);

        ReservationGuest reservationGuest = new ReservationGuest();
        reservationGuest.setReservation(reservation);
        reservationGuest.setReservationGuest(friend);
        reservationGuest.setStatus(INVITED);

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
