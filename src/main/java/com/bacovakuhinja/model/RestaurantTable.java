package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_tables")
@JsonIgnoreProperties(value = {"region"})
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rt_id")
    private Integer tableId;

    @Column(name = "rt_datax")
    private Double datax;

    @Column(name = "rt_datay")
    private Double datay;

    @Column(name = "rt_width")
    private Double width;

    @Column(name = "rt_height")
    private Double height;

    @Column(name = "rt_positions")
    private Integer positions;

    @ManyToOne
    @JoinColumn(name = "rt_region_id")
    private RestaurantRegion region;

    public RestaurantTable() {
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Double getDatax() {
        return datax;
    }

    public void setDatax(Double datax) {
        this.datax = datax;
    }

    public Double getDatay() {
        return datay;
    }

    public void setDatay(Double datay) {
        this.datay = datay;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getPositions() {
        return positions;
    }

    public void setPositions(Integer positions) {
        this.positions = positions;
    }

    public RestaurantRegion getRegion() {
        return region;
    }

    public void setRegion(RestaurantRegion region) {
        this.region = region;
    }

}
