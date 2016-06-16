package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;

public interface BillRepository extends JpaRepository <Bill, Integer> {
    Collection <Bill> findFirst5ByWaiter_UserIdOrderByPublishDateDesc(Integer waiterId);

    Collection <Bill> findByWaiter_UserIdAndPublishDateBetween(Integer userId, Date dateStart, Date dateEnd);
}
