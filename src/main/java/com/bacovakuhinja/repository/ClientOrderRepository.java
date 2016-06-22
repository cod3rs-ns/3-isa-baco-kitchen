package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {

    List<ClientOrder> findByTable_TableIdAndBill_BillIdAndStatusOrderByDateAsc(Integer tableId, Integer billId, String status);

    List<ClientOrder> findByTable_TableIdAndBill_BillIdAndStatusAndDeadlineLessThanOrderByDateAsc(Integer tableId, Integer billId, String status, Date deadline);

    ClientOrder findByReservation_reservationIdAndClientId(Integer reservationId, Integer userId);

    List<ClientOrder> findByReservation_reservationId(Integer reservationId);
}
