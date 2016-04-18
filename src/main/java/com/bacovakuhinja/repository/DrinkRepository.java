package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository <Drink, Integer> {
}
