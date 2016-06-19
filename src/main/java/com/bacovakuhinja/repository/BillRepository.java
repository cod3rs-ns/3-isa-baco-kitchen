package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;

public interface BillRepository extends JpaRepository <Bill, Integer> {
    Collection <Bill> findFirst5ByWaiter_UserIdOrderByPublishDateDesc(Integer waiterId);

    Collection <Bill> findByWaiter_UserIdAndPublishDateBetween(Integer userId, Date dateStart, Date dateEnd);

    @Query("SELECT b FROM Bill b WHERE b.waiter.restaurantID = ?1")
    Collection <Bill> findByRestaurant(Integer restaurantId);
}
