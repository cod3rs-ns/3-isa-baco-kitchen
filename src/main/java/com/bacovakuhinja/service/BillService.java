package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Bartender;
import com.bacovakuhinja.model.Bill;

import java.util.Collection;

/**
 * Created by Bojan on 30-May-16.
 */
public interface BillService {
    public Collection<Bill> findAll();

    public Bill findOne(Integer bId);

    public Bill create(Bill bill);

    public Bill update(Bill bill);

    public void delete(Integer bId);


}
