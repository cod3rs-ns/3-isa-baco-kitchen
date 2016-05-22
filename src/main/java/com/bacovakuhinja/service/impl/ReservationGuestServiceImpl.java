package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ReservationGuest;
import com.bacovakuhinja.service.ReservationGuestService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReservationGuestServiceImpl implements ReservationGuestService {

    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static final String INVITED  = "invited";
    private static final String OWNER    = "owner";

    @Override
    public Collection<ReservationGuest> findAll() {
        return null;
    }

    @Override
    public ReservationGuest create(ReservationGuest reservationGuest) {
        return null;
    }

    @Override
    public ReservationGuest update(ReservationGuest reservationGuest) {
        return null;
    }
}
