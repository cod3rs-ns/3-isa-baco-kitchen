package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository <Shift, Integer> {
}
