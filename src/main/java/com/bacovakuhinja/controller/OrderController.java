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
            value = "/api/orders/{restaurantId}/{tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> addOrder(@RequestBody ClientOrder order, @PathVariable("tableId") Integer tableId, @PathVariable("restaurantId") Integer restaurantId) {
        RestaurantTable table = tableService.findOne(tableId);
        order.setTable(table);

        ClientOrder newOrder = createOrder(order, restaurantId);

        return new ResponseEntity <ClientOrder>(newOrder, HttpStatus.OK);
    }

    private ClientOrder createOrder(ClientOrder order, int restaurantId){
        ClientOrder newOrder = clientOrderService.create(order);

        //notify for new items
        HashMap<String, ArrayList<OrderItem>> foodMap = new HashMap<String, ArrayList<OrderItem>>();
        ArrayList<OrderItem> foodList = new ArrayList<OrderItem>();
        HashMap<String, ArrayList<OrderItem>> drinkMap = new HashMap<String, ArrayList<OrderItem>>();
        ArrayList<OrderItem> drinkList = new ArrayList<OrderItem>();

        for (Iterator<OrderItem> it = order.getItems().iterator(); it.hasNext(); ) {
            OrderItem i = it.next();
            i.setOrder(newOrder);
            i.setMenuItem(menuItemService.findOne(i.getMenuItem().getMenuItemId()));
            i.setRestaurantId(restaurantId);
            OrderItem nItem = orderItemService.create(i);
            if (nItem.getMenuItem().getType().equals("food"))
                foodList.add(nItem);
            else
                drinkList.add(nItem);
        }

        foodMap.put("new", foodList);
        drinkMap.put("new", drinkList);
        this.template.convertAndSend("/subscribe/ActiveFood/" + restaurantId, foodMap);
        this.template.convertAndSend("/subscribe/ActiveDrink/" + restaurantId, drinkMap);

        return newOrder;
    }


    @RequestMapping(
            value = "/api/orders/{restaurantId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientOrder> editOrder(@RequestBody ClientOrder order, @PathVariable("restaurantId") Integer restaurantId) {

        ClientOrder oldOrder = clientOrderService.findOne(order.getOrderId());
        ClientOrder updated = updateOrder(order, oldOrder, restaurantId);
        clientOrderService.update(updated);

        return new ResponseEntity <ClientOrder>(updated, HttpStatus.OK);
    }

    private ClientOrder updateOrder(ClientOrder newOrder, ClientOrder oldOrder, int r_id){
        Set<OrderItem> oldItems = new HashSet<OrderItem>();
        oldItems.addAll(oldOrder.getItems());

        //notify for new items
        HashMap<String, ArrayList<OrderItem>> foodMap = new HashMap<String, ArrayList<OrderItem>>();
        ArrayList<OrderItem> foodNew = new ArrayList<OrderItem>();
        ArrayList<OrderItem> foodRemove = new ArrayList<OrderItem>();
        ArrayList<OrderItem> foodUpdate = new ArrayList<OrderItem>();


        Set<OrderItem> newItems = new HashSet<OrderItem>();

        for (Iterator<OrderItem> itNew = newOrder.getItems().iterator(); itNew.hasNext(); ) {
            OrderItem newItem = itNew.next();
            boolean flag = false;
            for(Iterator<OrderItem> itOld = oldItems.iterator(); itOld.hasNext();){
                OrderItem oldItem = itOld.next();
                if(oldItem.getMenuItem().getMenuItemId() == newItem.getMenuItem().getMenuItemId()){
                    if(oldItem.getAmount() != newItem.getAmount()) {
                        oldItem.setAmount(newItem.getAmount());
                        if (oldItem.getMenuItem().getType().equals("food"))
                            foodUpdate.add(oldItem);
                    }
                    newItems.add(oldItem);
                    flag = true;
                    break;
                }
            }

            if(!flag) {
                newItem.setOrder(oldOrder);
                newItem.setRestaurantId(r_id);
                newItem.setMenuItem(menuItemService.findOne(newItem.getMenuItem().getMenuItemId()));
                OrderItem item = orderItemService.create(newItem);
                newItems.add(item);
                if(newItem.getMenuItem().getType().equals("food"))
                    foodNew.add(newItem);
            }
        }

        oldOrder.getItems().clear();
        oldOrder.getItems().addAll(newItems);
        oldItems.removeAll(newItems);

        for(Iterator<OrderItem> itOld = oldItems.iterator(); itOld.hasNext();){
            OrderItem oi= itOld.next();
            if(oi.getMenuItem().getType().equals("food"))
                foodRemove.add(oi);
            orderItemService.delete(oi.getItemId());
        }

        foodMap.put("new", foodNew);
        foodMap.put("remove" , foodRemove);
        foodMap.put("update", foodUpdate);
        this.template.convertAndSend("/subscribe/ActiveFood/" + r_id, foodMap);

        return oldOrder;
    }
}

