package com.bacovakuhinja.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oi_id")
    private Integer itemId;

    @Column(name = "oi_state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_order_id")
    private ClientOrder order;

    @ManyToOne()
    @JoinColumn(name = "oi_drink_id")
    private Drink drink;

    @ManyToOne()
    @JoinColumn(name = "oi_food_id")
    private Food food;


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ClientOrder getOrder() {
        return order;
    }

    public void setOrder(ClientOrder order) {
        this.order = order;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", state='" + state + '\'' +
                ", order=" + order +
                ", drink=" + drink +
                ", food=" + food +
                '}';
    }
}
