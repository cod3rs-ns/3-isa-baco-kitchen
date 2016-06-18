package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ReservationTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationTableRepository extends JpaRepository<ReservationTable, Integer> {

    Collection<ReservationTable> findByReservation_reservationId(Integer reservationId);

}
