package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ReservationGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationGuestRepository extends JpaRepository<ReservationGuest, Integer> {
}
