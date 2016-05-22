package com.bacovakuhinja.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "offer_requests")
public class OfferRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "or_id")
    private Integer offerId;

    @Column(name = "or_offer")
    private String offer;

    @Column(name = "or_deadline")
    private Date deadline;

    @Column(name = "or_restaurant_id")
    private Integer restaurantId;

    @Column(name = "or_status")
    private String status;

    @Column(name = "or_accepted_offer_id")
    private String acceptedResponse;

    public OfferRequest() {
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcceptedResponse() {
        return acceptedResponse;
    }

    public void setAcceptedResponse(String acceptedResponse) {
        this.acceptedResponse = acceptedResponse;
    }

}
