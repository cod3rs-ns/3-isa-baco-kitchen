package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
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

import javax.servlet.http.HttpServletRequest;
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
        List<ClientOrder> orders = clientOrderService.getOrdersForBill(tableId);
        Bill b = new Bill();
        b.setPublishDate(new Date());
        double amount = 0;
        Waiter w = waiterService.findOne(8);

        //for which waiter
        ClientOrder co = orders.get(0);
        Date date = co.getDate();
        Date now = new Date();
        double diff = now.getTime() - date.getTime();


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


    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/waiter/bills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<Bill>> getWaiterBills(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        Collection<Bill> bills =  billService.findBillsByWaiter(waiter.getUserId());

        return new ResponseEntity <Collection<Bill>>(bills, HttpStatus.OK);
    }

    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/waiter/bill/{billId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <BillHelper> getBill(final HttpServletRequest request, @PathVariable("billId") Integer billId) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        Bill bill =  billService.findOne(billId);
        BillHelper helper = new BillHelper();
        helper.setDate(bill.getPublishDate());

        HashMap<Integer, BillItem> billItems = new HashMap<Integer, BillItem>();

        for(ClientOrder order : bill.getOrders()){
            for(OrderItem item : order.getItems()){
                MenuItem mi = item.getMenuItem();
                if(!billItems.containsKey(mi.getMenuItemId())) {
                    BillItem bi = new BillItem(mi.getMenuItemId(), mi, mi.getPrice() * item.getAmount(), item.getAmount());
                    billItems.put(bi.getId(), bi);
                }
                else {
                    BillItem curr = billItems.get(mi.getMenuItemId());
                    curr.setAmount(curr.getAmount() + item.getAmount());
                    curr.setPrice(curr.getPrice() + item.getAmount() * mi.getPrice());
                    billItems.put(curr.getId(), curr);
                }
            }
        }

        helper.setItems(billItems);
        helper.setWaiter(bill.getWaiter());

        return new ResponseEntity <BillHelper>(helper, HttpStatus.OK);
    }
}
