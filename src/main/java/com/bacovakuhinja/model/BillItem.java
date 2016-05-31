package com.bacovakuhinja.model;


public class BillItem {
    private Integer id;
    private MenuItem mi;
    private double price;
    private int amount;

    public BillItem(){
    }

    public BillItem(Integer id, MenuItem mi, double price, int amount){
        this.id = id;
        this.mi = mi;
        this.price = price;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MenuItem getMi() {
        return mi;
    }

    public void setMi(MenuItem mi) {
        this.mi = mi;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
