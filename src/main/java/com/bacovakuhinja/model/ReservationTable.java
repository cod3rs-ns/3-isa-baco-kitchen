package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation_tables")
public class ReservationTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rt_id")
    private Integer id;

    @JoinColumn(name = "rt_reservation_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Reservation reservation;

    @JoinColumn(name = "rt_table_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private RestaurantTable table;

    public ReservationTable() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }
}
