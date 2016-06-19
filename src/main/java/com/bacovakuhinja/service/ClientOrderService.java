package com.bacovakuhinja.service;


import com.bacovakuhinja.model.ClientOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ClientOrderService {

    Collection<ClientOrder> findAll();

    ClientOrder findOne(Integer eId);

    ClientOrder create(ClientOrder e);

    ClientOrder update(ClientOrder e);

    void delete(Integer eId);

    List<ClientOrder> getOrdersForBill(int tableId);

    List<ClientOrder> getOrdersFromReservation(int tableId);

    ClientOrder findByReservationAndUser(Integer reservationId, Integer guestId);

    Integer findByReservation(Integer reservationId);

    List<ClientOrder> findOrdersByReservation(Integer reservationId);
}
