package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ReservationTable;

import java.util.Collection;

public interface ReservationTableService {

    Collection<ReservationTable> findAll();

    Collection<ReservationTable> findAllByReservationId(Integer reservationId);

    ReservationTable save(ReservationTable table);

    void delete(ReservationTable table);
}
