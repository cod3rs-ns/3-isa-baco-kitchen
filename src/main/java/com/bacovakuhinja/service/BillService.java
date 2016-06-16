package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Bill;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Bojan on 30-May-16.
 */
public interface BillService {
    Collection <Bill> findAll();

    Bill findOne(Integer bId);

    Bill create(Bill bill);

    Bill update(Bill bill);

    void delete(Integer bId);

    Collection <Bill> findBillsByWaiter(Integer waiterId);

    Collection <Bill> findBillsByWaiterAndPublishDate(Integer userId, Date dateStart, Date dateEnd);

    Collection <Bill> findByRestaurant(Integer restaurantId);
}
