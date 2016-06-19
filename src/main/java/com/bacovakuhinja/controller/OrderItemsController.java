package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
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
    private EmployeeService employeeService;

    @Autowired
    private DailyScheduleService dailyScheduleService;

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
            value = "api/orderItems/itemsForPrepare/e={e_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> getAcceptedItemsByEmployee(@PathVariable("e_id") Integer employeeId) {
        Collection<OrderItem> items = orderItemService.findAcceptedItemsByEmployee(employeeId);
        return new ResponseEntity <Collection <OrderItem>>(items, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/orderItems/activeDrinks/r={r_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> getActiveDrinkByRestaurant(@PathVariable("r_id") Integer restaurantId) {
        Collection<OrderItem> items = orderItemService.findActiveDrinksByRestaurant(restaurantId);
        return new ResponseEntity <Collection <OrderItem>>(items, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/orderItems/i={itemId}/e={employeeId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItem> prepareOrderItem(@PathVariable("itemId") Integer itemId, @PathVariable("employeeId") Integer employeeId) {
        Employee emp = employeeService.findOne(employeeId);
        OrderItem item = orderItemService.findOne(itemId);

        if(item.getState().equals(Constants.OrderStatus.ACCEPTED))
            return new ResponseEntity <OrderItem>(HttpStatus.IM_USED);

        item.setEmployee(emp);
        item.setState(Constants.OrderStatus.ACCEPTED);

        OrderItem updatedItem;
        try {
             updatedItem = orderItemService.update(item);
        }
        catch (Exception e){
            return new ResponseEntity <OrderItem>(HttpStatus.IM_USED);
        }

        HashMap<String, ArrayList<OrderItem>> itemMap = new HashMap<String, ArrayList<OrderItem>>();
        ArrayList<OrderItem> items = new ArrayList<OrderItem>();
        items.add(item);
        itemMap.put(Constants.NotificationOrderStatus.REMOVE , items);

        if(item.getMenuItem().getType().equals(Constants.MenuItemType.FOOD)){
            this.template.convertAndSend("/subscribe/ActiveFood/" + emp.getRestaurantID(), itemMap);
        }
        else{
            this.template.convertAndSend("/subscribe/ActiveDrink/" + emp.getRestaurantID(), itemMap);
        }

        return new ResponseEntity <OrderItem>(updatedItem, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/orderItems/i={itemId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> finishOrderItem(@PathVariable("itemId") Integer itemId) {
        OrderItem item = orderItemService.findOne(itemId);
        if (item != null) {
            item.setState(Constants.OrderStatus.FINISHED);
            OrderItem updatedItem = orderItemService.update(item);
            Integer regionId = updatedItem.getOrder().getTable().getRegion().getRegionId();
            DailySchedule schedule = dailyScheduleService.findScheduleByRegionForNow(regionId);
            Employee e = schedule.getEmployee();
            if (e != null) {
                this.template.convertAndSend("/subscribe/finishOrder/" + e.getUserId(), item);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            }
        }
        return null;
    }


    @RequestMapping(
            value = "/api/orderItems/finished/region={regionId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OrderItem>> finishedOrderItem(@PathVariable("regionId") Integer regionId) {
        return new ResponseEntity<Collection<OrderItem>> (orderItemService.findFinishedItemsByRegion(regionId), HttpStatus.OK );
    }

    @RequestMapping(
            value = "/api/orderItems/deliver/i={itemId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItem> deliverOrderItem(@PathVariable("itemId") Integer itemId) {
        OrderItem item = orderItemService.findOne(itemId);
        item.setState(Constants.OrderStatus.DELIVERED);
        OrderItem updated = orderItemService.update(item);
        return new ResponseEntity<OrderItem> (updated, HttpStatus.OK );
    }

}