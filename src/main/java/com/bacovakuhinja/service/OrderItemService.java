package com.bacovakuhinja.service;


import com.bacovakuhinja.model.OrderItem;

import java.util.Collection;

public interface OrderItemService {

    Collection<OrderItem> findAll();

    OrderItem findOne(Integer oiId);

    OrderItem create(OrderItem oi);

    OrderItem update(OrderItem oi);

    void delete(Integer oiId);

    Collection<OrderItem> findOrderItemsByOrder(Integer orderId);

    Collection<OrderItem> findActiveFoodByRestaurant(Integer restaurantId);

    Collection<OrderItem> findAcceptedItemsByEmployee(Integer employeeId);

    Collection<OrderItem> findActiveDrinksByRestaurant(Integer restaurantId);

    Collection<OrderItem> findFinishedItemsByRegion(Integer regionId);
}
