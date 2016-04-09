package com.bacovakuhinja.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "restaurants")
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "r_id")
    private Integer restaurantId;

    @Column(name = "r_name")
    private String name;

    @Column(name = "r_info")
    private String info;

    @Column(name = "r_type")
    private String type;

    @Column(name = "r_time_start")
    private Integer startTime;

    @Column(name = "r_time_end")
    private Integer endTime;

    public Restaurant() {
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
