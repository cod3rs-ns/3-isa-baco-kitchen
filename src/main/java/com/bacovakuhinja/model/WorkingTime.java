package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "working_times")
public class WorkingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wt_id")
    private Integer workingTimeId;

    @Column(name = "wt_reg_start_h")
    private Integer regStartHours;

    @Column(name = "wt_reg_start_m")
    private Integer regStartMinutes;

    @Column(name = "wt_reg_end_h")
    private Integer regEndHours;

    @Column(name = "wt_reg_end_m")
    private Integer regEndMinutes;

    @Column(name = "wt_sat_start_h")
    private Integer satStartHours;

    @Column(name = "wt_sat_start_m")
    private Integer satStartMinutes;

    @Column(name = "wt_sat_end_h")
    private Integer satEndHours;

    @Column(name = "wt_sat_end_m")
    private Integer satEndMinutes;

    @Column(name = "wt_sun_start_h")
    private Integer sunStartHours;

    @Column(name = "wt_sun_start_m")
    private Integer sunStartMinutes;

    @Column(name = "wt_sun_end_h")
    private Integer sunEndHours;

    @Column(name = "wt_sun_end_m")
    private Integer sunEndMinutes;

    @Column(name = "wt_working_on_sat")
    private boolean workingOnSat;

    @Column(name = "wt_working_on_sun")
    private boolean workingOnSun;

    @Column(name = "wt_reg_reversed")
    private boolean regReversed;

    @Column(name = "wt_sat_reversed")
    private boolean satReversed;

    @Column(name = "wt_sun_reversed")
    private boolean sunReversed;

    @Column(name = "wt_restaurant_id")
    private Integer restaurantId;

    public WorkingTime() {
    }

    public Integer getWorkingTimeId() {
        return workingTimeId;
    }

    public void setWorkingTimeId(Integer workingTimeId) {
        this.workingTimeId = workingTimeId;
    }

    public Integer getRegStartHours() {
        return regStartHours;
    }

    public void setRegStartHours(Integer regStartHours) {
        this.regStartHours = regStartHours;
    }

    public Integer getRegStartMinutes() {
        return regStartMinutes;
    }

    public void setRegStartMinutes(Integer regStartMinutes) {
        this.regStartMinutes = regStartMinutes;
    }

    public Integer getRegEndHours() {
        return regEndHours;
    }

    public void setRegEndHours(Integer regEndHours) {
        this.regEndHours = regEndHours;
    }

    public Integer getRegEndMinutes() {
        return regEndMinutes;
    }

    public void setRegEndMinutes(Integer regEndMinutes) {
        this.regEndMinutes = regEndMinutes;
    }

    public Integer getSatStartHours() {
        return satStartHours;
    }

    public void setSatStartHours(Integer satStartHours) {
        this.satStartHours = satStartHours;
    }

    public Integer getSatStartMinutes() {
        return satStartMinutes;
    }

    public void setSatStartMinutes(Integer satStartMinutes) {
        this.satStartMinutes = satStartMinutes;
    }

    public Integer getSatEndHours() {
        return satEndHours;
    }

    public void setSatEndHours(Integer satEndHours) {
        this.satEndHours = satEndHours;
    }

    public Integer getSatEndMinutes() {
        return satEndMinutes;
    }

    public void setSatEndMinutes(Integer satEndMinutes) {
        this.satEndMinutes = satEndMinutes;
    }

    public Integer getSunStartHours() {
        return sunStartHours;
    }

    public void setSunStartHours(Integer sunStartHours) {
        this.sunStartHours = sunStartHours;
    }

    public Integer getSunStartMinutes() {
        return sunStartMinutes;
    }

    public void setSunStartMinutes(Integer sunStartMinutes) {
        this.sunStartMinutes = sunStartMinutes;
    }

    public Integer getSunEndHours() {
        return sunEndHours;
    }

    public void setSunEndHours(Integer sunEndHours) {
        this.sunEndHours = sunEndHours;
    }

    public Integer getSunEndMinutes() {
        return sunEndMinutes;
    }

    public void setSunEndMinutes(Integer sunEndMinutes) {
        this.sunEndMinutes = sunEndMinutes;
    }

    public boolean isWorkingOnSat() {
        return workingOnSat;
    }

    public void setWorkingOnSat(boolean workingOnSat) {
        this.workingOnSat = workingOnSat;
    }

    public boolean isWorkingOnSun() {
        return workingOnSun;
    }

    public void setWorkingOnSun(boolean workingOnSun) {
        this.workingOnSun = workingOnSun;
    }

    public boolean isRegReversed() {
        return regReversed;
    }

    public void setRegReversed(boolean regReversed) {
        this.regReversed = regReversed;
    }

    public boolean isSatReversed() {
        return satReversed;
    }

    public void setSatReversed(boolean satReversed) {
        this.satReversed = satReversed;
    }

    public boolean isSunReversed() {
        return sunReversed;
    }

    public void setSunReversed(boolean sunReversed) {
        this.sunReversed = sunReversed;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "WorkingTime{" +
                "workingTimeId=" + workingTimeId +
                ", regStartHours=" + regStartHours +
                ", regStartMinutes=" + regStartMinutes +
                ", regEndHours=" + regEndHours +
                ", regEndMinutes=" + regEndMinutes +
                ", satStartHours=" + satStartHours +
                ", satStartMinutes=" + satStartMinutes +
                ", satEndHours=" + satEndHours +
                ", satEndMinutes=" + satEndMinutes +
                ", sunStartHours=" + sunStartHours +
                ", sunStartMinutes=" + sunStartMinutes +
                ", sunEndHours=" + sunEndHours +
                ", sunEndMinutes=" + sunEndMinutes +
                ", workingOnSat=" + workingOnSat +
                ", workingOnSun=" + workingOnSun +
                ", regReversed=" + regReversed +
                ", satReversed=" + satReversed +
                ", sunReversed=" + sunReversed +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
