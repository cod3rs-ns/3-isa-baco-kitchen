package com.bacovakuhinja.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_managers")
@PrimaryKeyJoinColumn(name="sm_id")
public class SystemManager extends User{

    @Column(name = "sm_info")
    private String info;

    @OneToMany(mappedBy = "systemManager", fetch = FetchType.LAZY)
    private Set<Restaurant> restaurants = new HashSet<Restaurant>(0);

    public SystemManager(){
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
