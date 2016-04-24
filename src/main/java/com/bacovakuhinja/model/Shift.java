package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sh_id")
    private Integer shiftId;

    @Column(name = "sh_name")
    private String name;

    @Column(name = "sh_start")
    private Integer startTime;

    @Column(name = "sh_end")
    private Integer endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sh_restaurant_id")
    private Restaurant restaurant;

    public Shift() {
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
