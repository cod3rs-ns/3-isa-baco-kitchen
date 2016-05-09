package com.bacovakuhinja.controller;


import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.Food;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
public class OrderController {
    @Autowired
    private RestaurantTableService tableService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private DrinkService drinkService;

    @Autowired
    private FoodService foodService;


    @RequestMapping(
            value = "api/orders/{table_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<ClientOrder>> getTablesByRestaurant(@PathVariable("table_id") Integer tableId) {
        RestaurantTable table = tableService.findOne(tableId);
        Set<ClientOrder> tableOrders = table.getOrders();
        return new ResponseEntity <Collection <ClientOrder>>(tableOrders, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/orders/{tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> addOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId) {
        RestaurantTable table = tableService.findOne(tableId);
        order.setTable(table);

        ClientOrder newOrder = clientOrderService.create(order);

        for (Iterator<OrderItem> it = order.getItems().iterator(); it.hasNext(); ) {
            OrderItem i = it.next();
            i.setOrder(newOrder);
            if(i.getDrink()!= null)
                i.setDrink(drinkService.findOne(i.getDrink().getDrinkId()));
            else
                i.setFood(foodService.findOne(i.getFood().getFoodId()));

            orderItemService.create(i);
        }

        return new ResponseEntity <ClientOrder>(newOrder, HttpStatus.OK);
    }
}
