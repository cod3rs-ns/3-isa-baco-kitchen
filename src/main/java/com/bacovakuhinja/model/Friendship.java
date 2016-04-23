package com.bacovakuhinja.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "frienships")
public class Friendship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fs_id")
    private Integer id;

    @JoinColumn(name = "fs_first")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Guest sender;

    @JoinColumn(name = "fs_second")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Guest receiver;

    @Column(name = "fs_status")
    private String status;

    public Friendship() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Guest getSender() {
        return sender;
    }

    public void setSender(Guest sender) {
        this.sender = sender;
    }

    public Guest getReceiver() {
        return receiver;
    }

    public void setReceiver(Guest receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
