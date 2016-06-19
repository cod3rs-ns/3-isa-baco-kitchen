package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.BillService;
import com.bacovakuhinja.service.ClientOrderService;
import com.bacovakuhinja.service.WaiterService;
import com.bacovakuhinja.utility.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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

    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/bills/t={tableId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BillHelper> createBill(final HttpServletRequest request, @PathVariable("tableId") Integer tableId) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        List<ClientOrder> orders = clientOrderService.getOrdersForBill(tableId);
        Bill b = new Bill();
        b.setPublishDate(new Date());
        double amount = 0;
        b.setTotalAmount(amount);
        Bill created = billService.create(b);

        Set<ClientOrder> billOrders = new HashSet<ClientOrder>();
        HashMap<Integer, BillItem> billItems = new HashMap<Integer, BillItem>();
        HashMap<Integer, Integer> waitersCount = new HashMap<Integer, Integer>();
        for (Iterator<ClientOrder> it = orders.iterator(); it.hasNext(); ) {
            ClientOrder i = it.next();
            billOrders.add(i);
            i.setBill(created);
            if(i.getWaiterId()!=null){
                if(waitersCount.containsKey(i.getWaiterId()))
                    waitersCount.put(i.getWaiterId(), waitersCount.get(i.getWaiterId()) + 1);
                else
                    waitersCount.put(i.getWaiterId(), 1);
            }
            for (Iterator<OrderItem> ord = i.getItems().iterator(); ord.hasNext(); ) {
                OrderItem oi = ord.next();
                MenuItem mi = oi.getMenuItem();
                amount += oi.getAmount() * mi.getPrice();
                if (billItems.containsKey(mi.getMenuItemId())) {
                    BillItem curr = billItems.get(mi.getMenuItemId());
                    curr.setAmount(curr.getAmount() + oi.getAmount());
                    curr.setPrice(curr.getPrice() + oi.getAmount() * mi.getPrice());
                    billItems.put(curr.getId(), curr);
                } else {
                    BillItem bi = new BillItem(mi.getMenuItemId(), mi, mi.getPrice() * oi.getAmount(), oi.getAmount());
                    billItems.put(bi.getId(), bi);
                }
            }
        }

        //seting waiter who had more orders created for that table
        if(waitersCount.isEmpty()) {
            created.setWaiter(waiter);
        }
        else {
            LinkedHashMap<Integer, Integer> linked =  (LinkedHashMap<Integer, Integer>) MapUtil.sortByValue(waitersCount);
            Integer waiterId = linked.keySet().iterator().next();
            created.setWaiter(waiterService.findOne(waiterId));
        }

        BillHelper helper = new BillHelper();
        helper.setDate(created.getPublishDate());
        helper.setWaiter(created.getWaiter());
        helper.setItems(billItems);

        created.setTotalAmount(amount);
        created.setOrders(billOrders);
        billService.update(created);
        return new ResponseEntity <BillHelper>(helper, HttpStatus.OK);
    }


    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/waiter/bills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Bill>> getWaiterBills(final HttpServletRequest request) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        Collection <Bill> bills = billService.findBillsByWaiter(waiter.getUserId());

        return new ResponseEntity <Collection <Bill>>(bills, HttpStatus.OK);
    }

    @Authorization(role = "waiter")
    @RequestMapping(
            value = "/api/waiter/bill/{billId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <BillHelper> getBill(final HttpServletRequest request, @PathVariable("billId") Integer billId) {
        User user = (User) request.getAttribute("loggedUser");
        Waiter waiter = waiterService.findOne(user.getUserId());

        Bill bill = billService.findOne(billId);
        BillHelper helper = new BillHelper();
        helper.setDate(bill.getPublishDate());

        HashMap <Integer, BillItem> billItems = new HashMap <Integer, BillItem>();

        for (ClientOrder order : bill.getOrders()) {
            for (OrderItem item : order.getItems()) {
                MenuItem mi = item.getMenuItem();
                if (!billItems.containsKey(mi.getMenuItemId())) {
                    BillItem bi = new BillItem(mi.getMenuItemId(), mi, mi.getPrice() * item.getAmount(), item.getAmount());
                    billItems.put(bi.getId(), bi);
                } else {
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

    @RequestMapping(
            value = "api/bills/report/{waiter_id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Bill>> getWaiterBillsByDate(@PathVariable("waiter_id") Integer waiterId, @RequestBody ArrayList <Date> dates) {
        Date a = dates.get(0);
        Date b = dates.get(1);
        Collection <Bill> bills = billService.findBillsByWaiterAndPublishDate(waiterId, a, b);

        ResponseEntity <Collection <Bill>> re = null;
        try {
            re = new ResponseEntity <Collection <Bill>>(bills, HttpStatus.OK);
        } catch (Exception e) {
            // nothing
        }
        return re;
    }

    @RequestMapping(
            value = "api/bills/report/restaurant/{restaurant_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <Bill>> getBillsByRestaurantId(@PathVariable("restaurant_id") Integer restaurantId) {
        Collection <Bill> bills = billService.findByRestaurant(restaurantId);
        return new ResponseEntity <Collection <Bill>>(bills, HttpStatus.OK);
    }

}
