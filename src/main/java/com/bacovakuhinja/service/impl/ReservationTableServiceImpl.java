package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ReservationTable;
import com.bacovakuhinja.repository.ReservationTableRepository;
import com.bacovakuhinja.service.ReservationTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReservationTableServiceImpl implements ReservationTableService {

    @Autowired
    ReservationTableRepository reservationTableRepository;

    @Override
    public Collection<ReservationTable> findAll() {
        return reservationTableRepository.findAll();
    }

    @Override
    public Collection<ReservationTable> findAllByReservationId(Integer reservationId) {
        Collection<ReservationTable> reservationTables = new ArrayList<ReservationTable>();

        for (ReservationTable table : findAll()) {
            if (table.getReservation().getReservationId() == reservationId) {
                reservationTables.add(table);
            }
        }

        return reservationTables;
    }

    @Override
    public ReservationTable save(ReservationTable table) {
        return reservationTableRepository.save(table);
    }
}
