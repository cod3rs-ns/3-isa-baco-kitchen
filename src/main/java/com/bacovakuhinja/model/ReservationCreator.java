package com.bacovakuhinja.model;

import java.util.ArrayList;

public class ReservationCreator {
    private ArrayList<Integer> tables;
    private Reservation reservation;

    public ReservationCreator() {
        super();
    }


    public ArrayList<Integer> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Integer> tables) {
        this.tables = tables;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
