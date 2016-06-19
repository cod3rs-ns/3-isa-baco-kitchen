package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.repository.OrderItemRepository;
import com.bacovakuhinja.service.OrderItemService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Collection<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem findOne(Integer oiId) {
        return orderItemRepository.findOne(oiId);
    }

    @Override
    public OrderItem create(OrderItem oi) {
        return orderItemRepository.save(oi);
    }

    @Override
    public OrderItem update(OrderItem oi) {
        OrderItem order = orderItemRepository.findOne(oi.getItemId());
        if (order == null) return null;
        return orderItemRepository.save(oi);
    }

    @Override
    public void delete(Integer oiId) {
        orderItemRepository.delete(oiId);
    }

    @Override
    public Collection<OrderItem> findOrderItemsByOrder(Integer orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    @Override
    public Collection<OrderItem> findActiveFoodByRestaurant(Integer restaurantId) {
        return orderItemRepository.findByRestaurantIdAndStateAndMenuItem_Type(restaurantId, Constants.OrderStatus.CREATED, Constants.MenuItemType.FOOD);
    }

    @Override
    public Collection<OrderItem> findAcceptedItemsByEmployee(Integer employeeId) {
        return orderItemRepository.findByEmployee_UserIdAndState(employeeId, Constants.OrderStatus.ACCEPTED);
    }

    @Override
    public Collection<OrderItem> findActiveDrinksByRestaurant(Integer restaurantId) {
        return orderItemRepository.findByRestaurantIdAndStateAndMenuItem_Type(restaurantId, Constants.OrderStatus.CREATED, Constants.MenuItemType.DRINK);
    }

    @Override
    public Collection<OrderItem> findFinishedItemsByRegion(Integer regionId) {
        return orderItemRepository.findByStateAndOrder_Table_Region_RegionId(Constants.OrderStatus.FINISHED, regionId);
    }
}
