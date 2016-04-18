package com.bacovakuhinja.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "drinks")
@JsonIgnoreProperties(value = {"restaurant"})
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "d_id")
    private Integer drinkId;

    @Column(name = "d_info")
    private String info;

    @Column(name = "d_name")
    private String name;

    @Column(name = "d_price")
    private Double price;

    @Column(name = "d_type")
    private String type;

    @Column(name = "d_image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "d_restaurant_id")
    private Restaurant restaurant;

    public Drink() {
    }

    public Integer getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(Integer drinkId) {
        this.drinkId = drinkId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
