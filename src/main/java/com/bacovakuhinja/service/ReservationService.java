package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Reservation;

import java.util.Collection;

public interface ReservationService {

    public Collection<Reservation> findAll();

    public Collection<Reservation> findByRestaurantId(Integer restaurantId);

    public Reservation create(Reservation reservation);

    public void delete(Reservation reservation);
}
