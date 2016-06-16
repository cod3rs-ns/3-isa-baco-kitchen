package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Reservation;

import java.util.Collection;
import java.util.Date;

public interface ReservationService {

    Collection<Reservation> findAll();

    Reservation findOne(Integer reservationId);

    Collection<Reservation> findByOwnerId(Integer ownerId);

    Collection<Reservation> findVisitsByOwnerId(Integer ownerId);

    Collection<Reservation> findInvitationsByOwnerId(Integer ownerId);

    Collection<Reservation> findByRestaurantIdAndTime(Integer restaurantId, Date datetime, Integer length);

    Collection <Reservation> findByRestaurantId(Integer restaurantId);

    Reservation create(Reservation reservation);

    void delete(Reservation reservation);
}
