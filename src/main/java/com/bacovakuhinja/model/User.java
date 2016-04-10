package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is base class for all types of Users. It has all shared properties and all of them are based inside one
 * database table called 'users'.
 */

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private Integer userId;

    @Column(name = "u_fname")
    private String firstName;

    @Column(name = "u_lname")
    private String lastName;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_image")
    private String image;

    @JsonIgnore
    @Column(name = "u_password")
    private String password;

    public User(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
