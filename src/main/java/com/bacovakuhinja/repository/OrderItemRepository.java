package com.bacovakuhinja.repository;


import com.bacovakuhinja.model.OrderItem;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Collection<OrderItem> findByOrder_OrderId(Integer orderId);

    Collection<OrderItem> findByRestaurantIdAndStateAndMenuItem_Type(Integer restaurantId, String state, String type);

    Collection<OrderItem> findByEmployee_UserIdAndState(Integer employeeId, String state);

    Collection<OrderItem> findByStateAndOrder_Table_Region_RegionId(String state, Integer regionId);
}
