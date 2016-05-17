package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "daily_schedules")
public class DailySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ds_id")
    private Integer dailyScheduleId;

    @Column(name = "ds_day")
    private Date day;

    @ManyToOne
    @JoinColumn(name = "ds_region_id")
    private RestaurantRegion region;


    @ManyToOne
    @JoinColumn(name = "ds_employee_id")
    private Employee employee;

    @Column(name = "ds_restaurant_id")
    private Integer restaurantId;

    @Column(name = "ds_start_h")
    private Integer startHours;

    @Column(name = "ds_start_m")
    private Integer startMinutes;

    @Column(name = "ds_end_h")
    private Integer endHours;

    @Column(name = "ds_end_m")
    private Integer endMinutes;

    public DailySchedule() {
    }

    public Integer getDailyScheduleId() {
        return dailyScheduleId;
    }

    public void setDailyScheduleId(Integer dailyScheduleId) {
        this.dailyScheduleId = dailyScheduleId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public RestaurantRegion getRegion() {
        return region;
    }

    public void setRegion(RestaurantRegion region) {
        this.region = region;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
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
}
