package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.ReservationGuest;
import com.bacovakuhinja.repository.ReservationGuestRepository;
import com.bacovakuhinja.repository.ReservationRepository;
import com.bacovakuhinja.service.ReservationGuestService;
import com.bacovakuhinja.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final long HOUR_TO_MILISECONDS = 60*60*1000;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationGuestRepository reservationGuestRepository;

    @Autowired
    ReservationGuestService reservationGuestService;

    @Override
    public Collection<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findOne(Integer reservationId) {
        return reservationRepository.findOne(reservationId);
    }

    @Override
    public Collection<Reservation> findByOwnerId(Integer ownerId) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        Date now = new Date();
        for (Reservation reservation : findAll()) {
            if ((reservationGuestService.isOwner(reservation.getReservationId(), ownerId) || reservationGuestService.isAccepted(reservation.getReservationId(), ownerId)) && reservation.getReservationDateTime().after(now))
                reservations.add(reservation);
        }

        return reservations;
    }

    @Override
    public Collection<Reservation> findVisitsByOwnerId(Integer ownerId) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        Date now = new Date();
        for (Reservation reservation : findAll()) {
            if ((reservationGuestService.isOwner(reservation.getReservationId(), ownerId) || reservationGuestService.isAccepted(reservation.getReservationId(), ownerId)) && reservation.getReservationDateTime().before(now))
                reservations.add(reservation);
        }

        return reservations;
    }

    @Override
    public Collection<Reservation> findInvitationsByOwnerId(Integer ownerId) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        Date now = new Date();
        for (Reservation reservation : findAll()) {
            if (reservationGuestService.isInvited(reservation.getReservationId(), ownerId) && reservation.getReservationDateTime().after(now))
                reservations.add(reservation);
        }

        return reservations;
    }

    @Override
    public Collection<Reservation> findByRestaurantIdAndTime(Integer restaurantId, Date datetime, Integer length) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        Date resBeg = datetime;
        Date resEnd = new Date(datetime.getTime() + length*HOUR_TO_MILISECONDS);

        Date reservationBeg, reservationEnd;
        for (Reservation reservation : findByRestaurantId(restaurantId)) {

            reservationBeg = reservation.getReservationDateTime();
            reservationEnd = new Date(reservationBeg.getTime() + reservation.getLength()*HOUR_TO_MILISECONDS);

            if (isBetween(resBeg, reservationBeg, reservationEnd) || isBetween(resEnd, reservationBeg, reservationEnd)) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    private boolean isBetween(Date date, Date min, Date max) {
        return date.after(min) && date.before(max);
    }

    @Override
    public Collection <Reservation> findByRestaurantId(Integer restaurantId) {
        return reservationRepository.findByRestaurant_RestaurantId(restaurantId);
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
