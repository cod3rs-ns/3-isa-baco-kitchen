package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rv_id")
    private Integer reviewId;

    @Column(name = "rv_info")
    private String comment;

    @Column(name = "rv_service_rate")
    private Integer serviceRate;

    @Column(name = "rv_food_rate")
    private Integer foodRate;

    @Column(name = "rv_restaurant_rate")
    private Integer restaurantRate;

    @JoinColumn(name = "rv_guest_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Guest reviewer;

    @Column(name = "rv_reservation_id")
    private Integer reservation;

    public Review() {
        super();
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Integer serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Integer getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(Integer foodRate) {
        this.foodRate = foodRate;
    }

    public Integer getRestaurantRate() {
        return restaurantRate;
    }

    public void setRestaurantRate(Integer restaurantRate) {
        this.restaurantRate = restaurantRate;
    }

    public Guest getReviewer() {
        return reviewer;
    }

    public void setReviewer(Guest reviewer) {
        this.reviewer = reviewer;
    }

    public Integer getReservation() {
        return reservation;
    }

    public void setReservation(Integer reservation) {
        this.reservation = reservation;
    }
}
