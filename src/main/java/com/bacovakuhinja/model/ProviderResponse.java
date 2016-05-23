package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "provider_responses")
public class ProviderResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pr_id")
    private Integer responseId;

    @Column(name = "pr_price")
    private Double price;

    @Column(name = "pr_info")
    private String info;

    @Column(name = "pr_status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "pr_provider_id")
    private RestaurantProvider provider;

    @ManyToOne
    @JoinColumn(name = "pr_offer_id")
    private OfferRequest offer;

    public ProviderResponse() {
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RestaurantProvider getProvider() {
        return provider;
    }

    public void setProvider(RestaurantProvider provider) {
        this.provider = provider;
    }

    public OfferRequest getOffer() {
        return offer;
    }

    public void setOffer(OfferRequest offer) {
        this.offer = offer;
    }
}
