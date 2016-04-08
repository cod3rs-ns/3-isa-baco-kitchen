package com.bacovakuhinja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Restaurant Provider Entity
 * This entity in database table 'restaurant_providers' has primary key named 'rp_id' which is mapped on primary key
 * 'u_id' from table 'users' (foreign key) which also contains columns such as 'u_password', 'u_email' etc.
 * Annotation @PrimaryKeyJoinColumn joins data from 'users' with data from 'restaurant_providers' on 'rp_id -- u_id'
 * connection and creates instance of Restaurant Provider object. One should notice that there is no 'rp_id' and its
 * equivalent property inside RestaurantProvider.class because it is in User as 'userId'.
 */
@Entity
@Table(name = "restaurant_providers")
@PrimaryKeyJoinColumn(name = "rp_id") // 'rp_id' is from table 'restaurant_providers'
public class RestaurantProvider extends User {

    @Column(name = "rp_info")
    private String info;

    public RestaurantProvider() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
