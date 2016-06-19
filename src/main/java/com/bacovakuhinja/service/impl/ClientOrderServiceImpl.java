package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ClientOrder;
import com.bacovakuhinja.repository.ClientOrderRepository;
import com.bacovakuhinja.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ClientOrderServiceImpl implements ClientOrderService{

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Override
    public Collection<ClientOrder> findAll() {
        return clientOrderRepository.findAll();
    }

    @Override
    public ClientOrder findOne(Integer coId) {
        return clientOrderRepository.findOne(coId);
    }

    @Override
    public ClientOrder create(ClientOrder co) {
        return clientOrderRepository.save(co);
    }

    @Override
    public ClientOrder update(ClientOrder co) {
        ClientOrder order = clientOrderRepository.findOne(co.getOrderId());
        if (order == null) return null;
        return clientOrderRepository.save(co);
    }

    @Override
    public void delete(Integer coId) {
        clientOrderRepository.delete(coId);
    }

    @Override
    public List<ClientOrder> getOrdersForBill(int tableId) {
        return clientOrderRepository.findByTable_TableIdAndBill_BillIdAndStatusOrderByDateAsc(tableId, null, "ACCEPTED");
    }

    @Override
    public ClientOrder findByReservationAndUser(Integer reservationId, Integer guestId) {
        return clientOrderRepository.findByReservation_reservationIdAndClientId(reservationId, guestId);
    }

    @Override
    public Integer findByReservation(Integer reservationId) {
        return clientOrderRepository.findByReservation_reservationId(reservationId).size();
    }

    @Override
    public List<ClientOrder> getOrdersFromReservation(int tableId) {
        return clientOrderRepository.findByTable_TableIdAndBill_BillIdAndStatusOrderByDateAsc(tableId, null, "CREATED");
    }
}
