package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.repository.OrderItemRepository;
import com.bacovakuhinja.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
}
