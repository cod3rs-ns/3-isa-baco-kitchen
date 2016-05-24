package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Reservation;

import java.util.Collection;
import java.util.Date;

public interface ReservationService {

    public Collection<Reservation> findAll();

    public Reservation findOne(Integer reservationId);

    public Collection<Reservation> findByRestaurantId(Integer restaurantId);

    public Collection<Reservation> findByRestaurantIdAndTime(Integer restaurantId, Date datetime, Integer length);

    public Reservation create(Reservation reservation);

    public void delete(Reservation reservation);
}
