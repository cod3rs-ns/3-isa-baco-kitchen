package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "shift_templates")
public class ShiftTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sh_id")
    private Integer shiftId;

    @Column(name = "sh_name")
    private String name;

    @Column(name = "sh_start_h")
    private Integer startHours;

    @Column(name = "sh_start_m")
    private Integer startMinutes;

    @Column(name = "sh_end_h")
    private Integer endHours;

    @Column(name = "sh_end_m")
    private Integer endMinutes;

    @Column(name = "sh_restaurant_id")
    private Integer restaurantId;

    public ShiftTemplate() {
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

    public Integer getStartHours() {
        return startHours;
    }

    public void setStartHours(Integer startHours) {
        this.startHours = startHours;
    }

    public Integer getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(Integer startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Integer getEndHours() {
        return endHours;
    }

    public void setEndHours(Integer endHours) {
        this.endHours = endHours;
    }

    public Integer getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(Integer endMinutes) {
        this.endMinutes = endMinutes;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
