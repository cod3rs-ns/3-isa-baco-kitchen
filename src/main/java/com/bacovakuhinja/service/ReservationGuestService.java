package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ReservationGuest;

import java.util.Collection;

public interface ReservationGuestService {

    Collection<ReservationGuest> findAll();

    boolean isOwner(Integer reservationId, Integer userId);

    boolean isAccepted(Integer reservationId, Integer userId);

    ReservationGuest create(ReservationGuest reservationGuest);

    ReservationGuest update(ReservationGuest reservationGuest);
}
