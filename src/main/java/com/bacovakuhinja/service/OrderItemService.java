package com.bacovakuhinja.service;


import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.OrderItem;
import org.springframework.core.annotation.Order;

import java.util.Collection;
import java.util.List;

public interface OrderItemService {
    public Collection<OrderItem> findAll();

    public OrderItem findOne(Integer oiId);

    public OrderItem create(OrderItem oi);

    public OrderItem update(OrderItem oi);

    public void delete(Integer oiId);

    public Collection<OrderItem> findOrderItemsByOrder(Integer orderId);

    public Collection<OrderItem> findActiveFoodByRestaurant(Integer restaurantId);

    public Collection<OrderItem> findAcceptedItemsByEmployee(Integer employeeId);

    public Collection<OrderItem> findActiveDrinksByRestaurant(Integer restaurantId);
}
