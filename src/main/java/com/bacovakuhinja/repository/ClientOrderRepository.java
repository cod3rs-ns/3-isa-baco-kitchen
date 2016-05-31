package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
    Collection<ClientOrder> findByTable_TableIdAndBill_BillId(Integer tableId, Integer billId);
}
