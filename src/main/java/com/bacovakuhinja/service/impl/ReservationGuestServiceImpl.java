package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Reservation;
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
    public boolean isOwner(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, OWNER) != null);
    }

    @Override
    public boolean isInvited(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, INVITED) != null);
    }

    @Override
    public boolean isAccepted(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, ACCEPTED) != null);
    }

    @Override
    public ReservationGuest acceptInvitation(Integer reservationId, Integer userId) {
        ReservationGuest rg = reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestId(reservationId, userId);

        if (rg == null) return null;

        rg.setStatus(ACCEPTED);
        return reservationGuestRepository.save(rg);
    }

    @Override
    public ReservationGuest declineInvitation(Integer reservationId, Integer userId) {
        ReservationGuest rg = reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestId(reservationId, userId);

        if (rg == null) return null;

        rg.setStatus(REJECTED);
        return reservationGuestRepository.save(rg);
    }

    @Override
    public ReservationGuest create(ReservationGuest reservationGuest) {
        return reservationGuestRepository.save(reservationGuest);
    }

    @Override
    public ReservationGuest update(ReservationGuest reservationGuest) {
        return null;
    }

    @Override
    public String getOwner(Integer reservationId) {
        Guest owner = reservationGuestRepository.findByReservation_reservationIdAndStatus(reservationId, OWNER).getReservationGuest();
        return  owner.getFirstName() + " " + owner.getLastName();
    }
}
