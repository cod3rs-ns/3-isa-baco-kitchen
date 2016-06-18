package com.bacovakuhinja.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_managers")
@PrimaryKeyJoinColumn(name = "rm_id")
public class RestaurantManager extends User {

    @Column(name = "rm_info")
    private String info;

    @JoinColumn(name = "rm_restaurant_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public RestaurantManager() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
