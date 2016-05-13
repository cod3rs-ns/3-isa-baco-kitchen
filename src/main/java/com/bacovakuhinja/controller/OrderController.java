package com.bacovakuhinja.controller;


import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController {
    @Autowired
    private RestaurantTableService tableService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MenuItemService menuItemService;


    @RequestMapping(
            value = "api/orders/{table_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<ClientOrder>> getOrdersOfTable(@PathVariable("table_id") Integer tableId) {
        RestaurantTable table = tableService.findOne(tableId);
        Set<ClientOrder> tableOrders = table.getOrders();
        return new ResponseEntity <Collection <ClientOrder>>(tableOrders, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/order/{order_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <HashMap<String,  Integer>> getOrderById(@PathVariable("order_id") Integer orderId) {
        ClientOrder order = clientOrderService.findOne(orderId);
        Set <OrderItem> items =  order.getItems();
        HashMap <String, Integer> countItems = new HashMap<String, Integer>();

        /*
        for (Iterator<OrderItem> it = items.iterator(); it.hasNext(); ) {
            OrderItem i = it.next();
            if(i.getDrink()!= null && countItems.containsKey("d"+i.getDrink().getDrinkId())){
                int count = countItems.get("d" + i.getDrink().getDrinkId());
                countItems.put("d" + i.getDrink().getDrinkId(), count + 1);
            }
            else if (i.getMenuItem() != null && countItems.containsKey("f" + i.getMenuItem().getMenuItemId())){
                int count = countItems.get("f" + i.getMenuItem().getMenuItemId());
                countItems.put("f" + i.getMenuItem().getMenuItemId(), count + 1);
            }
            else{
                if(i.getDrink() != null)
                    countItems.put("d" + i.getDrink().getDrinkId(), 1);
                else
                    countItems.put("f" + i.getMenuItem().getMenuItemId(), 1);
            }

        }
        */
        return new ResponseEntity <HashMap <String, Integer>>(countItems, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/orders/{tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> addOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId) {
        RestaurantTable table = tableService.findOne(tableId);
        order.setTable(table);

        ClientOrder newOrder = createOrder(order);

        return new ResponseEntity <ClientOrder>(newOrder, HttpStatus.OK);
    }

    private ClientOrder createOrder(ClientOrder order){
        ClientOrder newOrder = clientOrderService.create(order);

        for (Iterator<OrderItem> it = order.getItems().iterator(); it.hasNext(); ) {
            OrderItem i = it.next();
            i.setOrder(newOrder);
            /*
            if(i.getDrink()!= null)
                i.setDrink(drinkService.findOne(i.getDrink().getDrinkId()));
            else
                i.setMenuItem(menuItemService.findOne(i.getMenuItem().getMenuItemId()));
            */
            orderItemService.create(i);
        }

        return newOrder;
    }

    @RequestMapping(
            value = "/api/orders/{tableId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> editOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId) {
        clientOrderService.delete(order.getOrderId());

        RestaurantTable table = tableService.findOne(tableId);
        order.setTable(table);

        System.out.println(order.getOrderId());
        ClientOrder newOrder = createOrder(order);

        return new ResponseEntity <ClientOrder>(newOrder, HttpStatus.OK);
    }
}

