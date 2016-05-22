package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ReservationGuest;
import com.bacovakuhinja.repository.ReservationGuestRepository;
import com.bacovakuhinja.service.ReservationGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReservationGuestServiceImpl implements ReservationGuestService {

    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static final String INVITED  = "invited";
    private static final String OWNER    = "owner";

    @Autowired
    ReservationGuestRepository reservationGuestRepository;

    @Override
    public Collection<ReservationGuest> findAll() {
        return reservationGuestRepository.findAll();
    }

    @Override
    public ReservationGuest create(ReservationGuest reservationGuest) {
        return reservationGuestRepository.save(reservationGuest);
    }

    @Override
    public ReservationGuest update(ReservationGuest reservationGuest) {
        // ReservationGuest rg =
        return null;
    }
}
