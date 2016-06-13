package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ReservationGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationGuestRepository extends JpaRepository<ReservationGuest, Integer> {
    ReservationGuest findByReservation_reservationIdAndReservationGuest_guestId(Integer reservationId, Integer guestId);
}
