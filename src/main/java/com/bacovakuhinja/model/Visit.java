package com.bacovakuhinja.model;


public class Visit {

    private Reservation reservation;
    private Review review;
    private String name;
    private String imageSrc;


    public Visit(Reservation reservation, Review review, String name, String imageSrc) {
        this.reservation = reservation;
        this.review = review;
        this.name = name;
        this.imageSrc = imageSrc;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
