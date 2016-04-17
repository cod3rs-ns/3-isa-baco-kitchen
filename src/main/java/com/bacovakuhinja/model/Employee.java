package com.bacovakuhinja.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "e_id")
public class Employee extends User{
    @Column(name="e_bday")
    private Date birthday;

    @Column(name="e_dress_size")
    private String dressSize;

    @Column(name="e_shoes_size")
    private String shoesSize;


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDressSize() {
        return dressSize;
    }

    public void setDressSize(String dressSize) {
        this.dressSize = dressSize;
    }

    public String getShoesSize() {
        return shoesSize;
    }

    public void setShoesSize(String shoesSize) {
        this.shoesSize = shoesSize;
    }

    public void update(Employee newEmployee){
        this.setFirstName(newEmployee.getFirstName());
        this.setLastName(newEmployee.getLastName());
        this.setBirthday(newEmployee.getBirthday());
        this.setShoesSize(newEmployee.getShoesSize());
        this.setDressSize(newEmployee.getDressSize());
    }
}
