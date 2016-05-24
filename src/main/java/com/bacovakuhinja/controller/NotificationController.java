package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.Greeting;
import com.bacovakuhinja.model.HelloMessage;
import com.bacovakuhinja.model.OrderItem;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class NotificationController {

    @MessageMapping("/connectFood/{r_id}")
    @SendTo("/subscribe/ActiveFood/{r_id}")
    public HashMap<String, ArrayList<OrderItem>> getActiveFood(@DestinationVariable String r_id, HelloMessage message) throws Exception {
        Thread.sleep(3000);
        System.out.println(r_id);
        HashMap<String, ArrayList<OrderItem>> mapa = new HashMap<String, ArrayList<OrderItem>>();
        return mapa;
    }

    @MessageMapping("/connectDrink/{r_id}")
    @SendTo("/subscribe/ActiveDrink/{r_id}")
    public HashMap<String, ArrayList<OrderItem>> getActiveDrink(@DestinationVariable String r_id) throws Exception {
        Thread.sleep(3000);
        System.out.println(r_id);
        HashMap<String, ArrayList<OrderItem>> mapa = new HashMap<String, ArrayList<OrderItem>>();
        return mapa;
    }

    @MessageMapping("/finishOrder/{waiter_id}")
    @SendTo("/subscribe/finishOrder/{waiter_id}")
    public OrderItem getFinishOrder(@DestinationVariable String waiter_id) throws Exception {
        Thread.sleep(3000);
        System.out.println(waiter_id);
        return new OrderItem();
    }
}
