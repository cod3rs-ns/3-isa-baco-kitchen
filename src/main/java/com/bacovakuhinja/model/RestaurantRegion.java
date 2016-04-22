package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnore
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<RestaurantTable> tables = new HashSet <RestaurantTable>(0);

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

    @JsonProperty
    public Set <RestaurantTable> getTables() {
        return tables;
    }

    @JsonIgnore
    public void setTables(Set <RestaurantTable> tables) {
        this.tables = tables;
    }
}
