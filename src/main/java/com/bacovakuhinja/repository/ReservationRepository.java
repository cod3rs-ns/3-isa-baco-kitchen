package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
