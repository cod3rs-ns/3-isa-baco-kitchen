package com.bacovakuhinja.model;

/**
 * Created by Bojan on 30-May-16.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bills")
@PrimaryKeyJoinColumn(name = "bl_id")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bl_id")
    private Integer billId;

    @Column(name = "bl_publish_date")
    private Date publishDate;

    @Column(name = "bl_total_amount")
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bl_waiter_id")
    private Waiter waiter;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ClientOrder> orders = new HashSet<ClientOrder>(0);

    public Bill(){

    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public Set<ClientOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<ClientOrder> orders) {
        this.orders = orders;
    }
}
