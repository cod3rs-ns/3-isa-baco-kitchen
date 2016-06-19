package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Collection<Reservation> findByRestaurant_RestaurantId(Integer restaurantId);

}
