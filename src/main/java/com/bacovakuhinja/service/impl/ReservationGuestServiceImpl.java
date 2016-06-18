package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.ReservationGuest;
import com.bacovakuhinja.repository.ReservationGuestRepository;
import com.bacovakuhinja.service.ReservationGuestService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReservationGuestServiceImpl implements ReservationGuestService {

    @Autowired
    ReservationGuestRepository reservationGuestRepository;

    @Override
    public Collection<ReservationGuest> findAll() {
        return reservationGuestRepository.findAll();
    }

    @Override
    public Collection<ReservationGuest> findAllByReservationAndStatus(Integer reservationId, String status) {
        return reservationGuestRepository.findByReservation_reservationIdAndStatus(reservationId, status);
    }

    @Override
    public boolean isOwner(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, Constants.Reservation.OWNER) != null);
    }

    @Override
    public boolean isInvited(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, Constants.Reservation.INVITED) != null);
    }

    @Override
    public boolean isAccepted(Integer reservationId, Integer userId) {
        return (reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(reservationId, userId, Constants.Reservation.ACCEPTED) != null);
    }

    @Override
    public ReservationGuest acceptInvitation(Integer reservationId, Integer userId) {
        ReservationGuest rg = reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestId(reservationId, userId);

        if (rg == null) return null;

        rg.setStatus(Constants.Reservation.ACCEPTED);
        return reservationGuestRepository.save(rg);
    }

    @Override
    public ReservationGuest declineInvitation(Integer reservationId, Integer userId) {
        ReservationGuest rg = reservationGuestRepository.findByReservation_reservationIdAndReservationGuest_guestId(reservationId, userId);

        if (rg == null) return null;

        rg.setStatus(Constants.Reservation.REJECTED);
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
        Guest owner = reservationGuestRepository.findByReservation_reservationIdAndStatus(reservationId, Constants.Reservation.OWNER).iterator().next().getReservationGuest();
        return  owner.getFirstName() + " " + owner.getLastName();
    }

    @Override
    public void delete(ReservationGuest reservationGuest) {
        reservationGuestRepository.delete(reservationGuest);
    }

    @Override
    public Integer numberOfReservationGuests(Integer reservationId) {
        return 1 + reservationGuestRepository.findByReservation_reservationIdAndStatus(reservationId, Constants.Reservation.ACCEPTED).size();
    }
}
