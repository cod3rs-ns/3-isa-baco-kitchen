package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "guests_on_reservations")
public class ReservationGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gos_id")
    private Integer id;

    @JoinColumn(name = "gos_guest_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Guest reservationGuest;

    @JoinColumn(name = "gos_reservation_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Reservation reservation;

    @Column(name = "gos_status")
    private String status;

    public ReservationGuest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Guest getReservationGuest() {
        return reservationGuest;
    }

    public void setReservationGuest(Guest reservationGuest) {
        this.reservationGuest = reservationGuest;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
