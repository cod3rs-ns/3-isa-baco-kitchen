package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.BartenderService;
import com.bacovakuhinja.service.BillService;
import com.bacovakuhinja.service.ClientOrderService;
import com.bacovakuhinja.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.HashMapChangeSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    private WaiterService waiterService;

    @RequestMapping(
            value = "/api/bills/t={tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BillHelper> createBill(@PathVariable("tableId") Integer tableId) {
        Collection<ClientOrder> orders = clientOrderService.getOrdersForBill(tableId);
        Bill b = new Bill();
        b.setPublishDate(new Date());
        double amount = 0;
        Waiter w = waiterService.findOne(8);
        b.setTotalAmount(amount);
        b.setWaiter(w);
        Bill created = billService.create(b);

        Set<ClientOrder> billOrders = new HashSet<ClientOrder>();
        HashMap<Integer, BillItem> billItems = new HashMap<Integer, BillItem>();
        for (Iterator<ClientOrder> it = orders.iterator(); it.hasNext(); ) {
            ClientOrder i = it.next();
            billOrders.add(i);
            i.setBill(created);
            for (Iterator<OrderItem> ord = i.getItems().iterator(); ord.hasNext(); ) {
                OrderItem oi = ord.next();
                MenuItem mi = oi.getMenuItem();
                amount += oi.getAmount() * mi.getPrice();
                if(billItems.containsKey(mi.getMenuItemId())) {
                    BillItem curr = billItems.get(mi.getMenuItemId());
                    curr.setAmount(curr.getAmount() + oi.getAmount());
                    curr.setPrice(curr.getPrice() + oi.getAmount() * mi.getPrice());
                    billItems.put(curr.getId(), curr);
                }
                else {
                    BillItem bi = new BillItem(mi.getMenuItemId(), mi, mi.getPrice() * oi.getAmount(), oi.getAmount());
                    billItems.put(bi.getId(), bi);
                }
            }
        }

        BillHelper helper = new BillHelper();
        helper.setDate(created.getPublishDate());
        helper.setWaiter(created.getWaiter());
        helper.setItems(billItems);

        created.setTotalAmount(amount);
        created.setOrders(billOrders);
        billService.update(created);
        return new ResponseEntity<BillHelper>(helper, HttpStatus.OK);
    }
}
