package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ReservationTable;

import java.util.Collection;

public interface ReservationTableService {

    public Collection<ReservationTable> findAll();

    public Collection<ReservationTable> findAllByReservationId(Integer reservationId);

    public ReservationTable save(ReservationTable table);
}
