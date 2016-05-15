package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.Employee;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.service.ClientOrderService;
import com.bacovakuhinja.service.EmployeeService;
import com.bacovakuhinja.service.OrderItemService;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


@RestController
public class OrderItemsController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private SimpMessagingTemplate template;


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

    @RequestMapping(
            value = "api/orderItems/foodForPrepare/e={e_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> getAcceptedItemsByEmployee(@PathVariable("e_id") Integer employeeId) {
        Collection<OrderItem> items = orderItemService.findAcceptedItemsByEmployee(employeeId);
        return new ResponseEntity <Collection <OrderItem>>(items, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/orderItems/i={itemId}/e={employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItem> prepareOrderItem(@PathVariable("itemId") Integer itemId, @PathVariable("employeeId") Integer employeeId) {
        Employee emp = employeeService.findOne(employeeId);
        OrderItem item = orderItemService.findOne(itemId);
        item.setEmployee(emp);
        item.setState("ACCEPTED");
        OrderItem updatedItem = orderItemService.update(item);

        HashMap<String, ArrayList<OrderItem>> itemMap = new HashMap<String, ArrayList<OrderItem>>();
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();
        items.add(item);
        itemMap.put("remove" , items);

        if(item.getMenuItem().getType().equals("food")){
            this.template.convertAndSend("/subscribe/ActiveFood/" + emp.getRestaurantID(), itemMap);
        }

        return new ResponseEntity <OrderItem>(updatedItem, HttpStatus.OK);
    }

}
