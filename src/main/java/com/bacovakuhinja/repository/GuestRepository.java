package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}
