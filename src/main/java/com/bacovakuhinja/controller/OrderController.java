package com.bacovakuhinja.controller;



import com.bacovakuhinja.model.*;
import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.model.OrderItem;
import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public ResponseEntity <ClientOrder> getOrderById(@PathVariable("order_id") Integer orderId) {
        ClientOrder order = clientOrderService.findOne(orderId);
        return new ResponseEntity <ClientOrder>(order, HttpStatus.OK);
    }

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(
            value = "/api/orders/{tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> addOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId) {
        RestaurantTable table = tableService.findOne(tableId);
        order.setTable(table);

        ClientOrder newOrder = createOrder(order);

        this.template.convertAndSend("/topic/greetings", new Greeting("This is Send From Server"));

        return new ResponseEntity <ClientOrder>(newOrder, HttpStatus.OK);
    }

    private ClientOrder createOrder(ClientOrder order){
        ClientOrder newOrder = clientOrderService.create(order);

        for (Iterator<OrderItem> it = order.getItems().iterator(); it.hasNext(); ) {
            OrderItem i = it.next();
            i.setOrder(newOrder);
            i.setMenuItem(menuItemService.findOne(i.getMenuItem().getMenuItemId()));
            orderItemService.create(i);
        }

        return newOrder;
    }


    @RequestMapping(
            value = "/api/orders/{tableId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> editOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId) {
        ClientOrder oldOrder = clientOrderService.findOne(order.getOrderId());
        ClientOrder updated = updateOrder(order, oldOrder);
        clientOrderService.update(updated);

        return new ResponseEntity <ClientOrder>(updated, HttpStatus.OK);
    }

    private ClientOrder updateOrder(ClientOrder newOrder, ClientOrder oldOrder){
        Set<OrderItem> oldItems = new HashSet<OrderItem>();
        oldItems.addAll(oldOrder.getItems());

        Set<OrderItem> newItems = new HashSet<OrderItem>();

        for (Iterator<OrderItem> itNew = newOrder.getItems().iterator(); itNew.hasNext(); ) {
            OrderItem newItem = itNew.next();
            boolean flag = false;
            for(Iterator<OrderItem> itOld = oldItems.iterator(); itOld.hasNext();){
                OrderItem oldItem = itOld.next();
                if(oldItem.getMenuItem().getMenuItemId() == newItem.getMenuItem().getMenuItemId()){
                    oldItem.setAmount(newItem.getAmount());
                    newItems.add(oldItem);
                    flag = true;
                    break;
                }
            }

            if(!flag) {
                newItem.setOrder(oldOrder);
                newItem.setMenuItem(menuItemService.findOne(newItem.getMenuItem().getMenuItemId()));
                OrderItem item = orderItemService.create(newItem);
                newItems.add(item);
            }
        }

        oldOrder.getItems().clear();
        oldOrder.getItems().addAll(newItems);
        oldItems.removeAll(newItems);

        for(Iterator<OrderItem> itOld = oldItems.iterator(); itOld.hasNext();){
            OrderItem oi= itOld.next();
            orderItemService.delete(oi.getItemId());
        }

        return oldOrder;
    }
}

