package com.bacovakuhinja.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "work_periods")
public class WorkPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wp_id")
    private Integer workPeriodId;

    @Column(name = "wp_start")
    private Date start;

    @Column(name = "wp_end")
    private Date end;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "wp_restaurant_id")
    private Restaurant restaurant;

    public WorkPeriod() {
    }

    public Integer getWorkPeriodId() {
        return workPeriodId;
    }

    public void setWorkPeriodId(Integer workPeriodId) {
        this.workPeriodId = workPeriodId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
