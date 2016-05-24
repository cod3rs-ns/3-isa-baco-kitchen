package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.ReservationGuest;

import java.util.Collection;

public interface ReservationGuestService {

    public Collection<ReservationGuest> findAll();

    public boolean isOwner(Integer reservationId, Integer userId);

    public ReservationGuest create(ReservationGuest reservationGuest);

    public ReservationGuest update(ReservationGuest reservationGuest);
}
