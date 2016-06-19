package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ReservationGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationGuestRepository extends JpaRepository<ReservationGuest, Integer> {

    ReservationGuest findByReservation_reservationIdAndReservationGuest_guestId(Integer reservationId, Integer guestId);

    Collection<ReservationGuest> findByReservation_reservationIdAndStatus(Integer reservationId, String status);

    ReservationGuest findByReservation_reservationIdAndReservationGuest_guestIdAndStatus(Integer reservationId, Integer guestId, String status);
}
