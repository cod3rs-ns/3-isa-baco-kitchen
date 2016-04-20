package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_regions")
@JsonIgnoreProperties(value = {"restaurant"})
public class RestaurantRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rr_id")
    private Integer regionId;

    @Column(name = "rr_name")
    private String name;

    @Column(name = "rr_color")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rr_restaurant_id")
    private Restaurant restaurant;

    public RestaurantRegion() {
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
