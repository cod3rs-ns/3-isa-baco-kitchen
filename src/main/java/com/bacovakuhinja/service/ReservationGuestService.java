package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ReservationGuest;

import java.util.Collection;

public interface ReservationGuestService {

    public Collection<ReservationGuest> findAll();

    public ReservationGuest create(ReservationGuest reservationGuest);

    public ReservationGuest update(ReservationGuest reservationGuest);
}
