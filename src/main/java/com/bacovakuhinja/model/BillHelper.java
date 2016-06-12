package com.bacovakuhinja.model;

import java.util.Date;
import java.util.HashMap;

public class BillHelper {
    private HashMap<Integer, BillItem> items;
    private Waiter waiter;
    private Date date;

    public BillHelper(){

    }

    public BillHelper(HashMap<Integer, BillItem> items, Waiter waiter, Date date){
        this.items = items;
        this.waiter = waiter;
        this.date = date;
    }

    public HashMap<Integer, BillItem> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, BillItem> items) {
        this.items = items;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
