package com.bacovakuhinja.repository;


import com.bacovakuhinja.model.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookRepository  extends JpaRepository<Cook, Integer> {
}
