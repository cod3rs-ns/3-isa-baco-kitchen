package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.repository.ReservationRepository;
import com.bacovakuhinja.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Collection<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Collection<Reservation> findByRestaurantId(Integer restaurantId) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        for (Reservation reservation : findAll()) {
            if (reservation.getRestaurant().getRestaurantId() == restaurantId) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
