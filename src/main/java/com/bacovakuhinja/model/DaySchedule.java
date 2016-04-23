package com.bacovakuhinja.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "day_schedules")
public class DaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ds_id")
    private Integer dayScheduleId;

    @Column(name = "ds_day")
    private Date day;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ds_shift_id")
    private Shift shift;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ds_work_period_id")
    private WorkPeriod workPeriod;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ds_region_id")
    private RestaurantRegion region;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ds_employee_id")
    private Employee employee;

    public DaySchedule() {
    }

    public Integer getDayScheduleId() {
        return dayScheduleId;
    }

    public void setDayScheduleId(Integer dayScheduleId) {
        this.dayScheduleId = dayScheduleId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public WorkPeriod getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(WorkPeriod workPeriod) {
        this.workPeriod = workPeriod;
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
}
