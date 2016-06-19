package com.bacovakuhinja.model;


public class ReservationHelper {

    private Reservation reservation;
    private Review review;
    private String name;
    private String imageSrc;
    private String inviter;
    private Integer restaurantId;
    private Integer order;
    private Integer acceptedNo;
    private boolean owner;


    public ReservationHelper(Reservation reservation, Review review, String name, String imageSrc) {
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

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getAcceptedNo() {
        return acceptedNo;
    }

    public void setAcceptedNo(Integer acceptedNo) {
        this.acceptedNo = acceptedNo;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
