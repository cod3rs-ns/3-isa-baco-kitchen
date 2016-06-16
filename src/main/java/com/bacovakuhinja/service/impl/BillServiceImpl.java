package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Bill;
import com.bacovakuhinja.repository.BillRepository;
import com.bacovakuhinja.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Bojan on 30-May-16.
 */
@Service
public class BillServiceImpl implements BillService{

    @Autowired
    private BillRepository billRepository;

    @Override
    public Collection<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill findOne(Integer bId) {
        return billRepository.findOne(bId);
    }

    @Override
    public Bill create(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill update(Bill bill) {
        Bill billPersistent = billRepository.findOne(bill.getBillId());
        if (billPersistent == null) return null;
        return billRepository.save(billPersistent);
    }

    @Override
    public void delete(Integer bId) {
        billRepository.delete(bId);
    }

    @Override
    public Collection<Bill> findBillsByWaiter(Integer waiterId) {
        return billRepository.findFirst5ByWaiter_UserIdOrderByPublishDateDesc(waiterId);
    }

    @Override
    public Collection <Bill> findBillsByWaiterAndPublishDate(Integer userId, Date dateStart, Date dateEnd) {
        return billRepository.findByWaiter_UserIdAndPublishDateBetween(userId, dateStart, dateEnd);
    }
}
