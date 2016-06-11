package com.bacovakuhinja.model;


public class Visit {

    private Reservation reservation;
    private Review review;


    public Visit(Reservation reservation, Review review) {
        this.reservation = reservation;
        this.review = review;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
