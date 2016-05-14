package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.service.ClientOrderService;
import com.bacovakuhinja.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
public class OrderItemsController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ClientOrderService clientOrderService;

    @RequestMapping(
            value = "api/orderItems/{order_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> getOrderItemsOfOrder(@PathVariable("order_id") Integer orderId) {
        Collection<OrderItem> items = orderItemService.findOrderItemsByOrder(orderId);
        return new ResponseEntity <Collection <OrderItem>>(items, HttpStatus.OK);
    }


    @RequestMapping(
            value = "api/orderItems/activeFood/r={r_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> getActiveFoodByRestaurant(@PathVariable("r_id") Integer restaurantId) {
        Collection<OrderItem> items = orderItemService.findActiveFoodByRestaurant(restaurantId);
        return new ResponseEntity <Collection <OrderItem>>(items, HttpStatus.OK);
    }

}
