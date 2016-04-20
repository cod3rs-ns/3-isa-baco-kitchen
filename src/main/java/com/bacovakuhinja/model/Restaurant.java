package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
@JsonIgnoreProperties(value = {"systemManager"})
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "r_sm_id")
    private SystemManager systemManager;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<Food> foodMenu = new HashSet<Food>(0);

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<Drink> drinksMenu = new HashSet<Drink>(0);

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<RestaurantRegion> regions = new HashSet<RestaurantRegion>(0);

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

    public SystemManager getSystemManager() { return systemManager; }

    public void setSystemManager(SystemManager systemManager) { this.systemManager = systemManager; }

    @JsonProperty
    public Set <Food> getFoodMenu() {
        return foodMenu;
    }
    @JsonIgnore
    public void setFoodMenu(Set <Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    @JsonProperty
    public Set <Drink> getDrinksMenu() {
        return drinksMenu;
    }
    @JsonIgnore
    public void setDrinksMenu(Set <Drink> drinksMenu) {
        this.drinksMenu = drinksMenu;
    }

    @JsonProperty
    public Set <RestaurantRegion> getRegions() {
        return regions;
    }

    @JsonIgnore
    public void setRegions(Set <RestaurantRegion> regions) {
        this.regions = regions;
    }
}
