package com.bacovakuhinja.repository;


import com.bacovakuhinja.model.Bartender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BartenderRepository extends JpaRepository<Bartender, Integer> {
}
