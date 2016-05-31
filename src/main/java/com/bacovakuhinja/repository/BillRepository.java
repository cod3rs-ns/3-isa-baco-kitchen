package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Bojan on 30-May-16.
 */
public interface BillRepository extends JpaRepository<Bill, Integer> {
}
