package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingTimeRepository extends JpaRepository <WorkingTime, Integer> {
}
