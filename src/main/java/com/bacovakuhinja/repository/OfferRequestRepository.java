package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.OfferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OfferRequestRepository extends JpaRepository <OfferRequest, Integer> {

    Collection <OfferRequest> findByRestaurantId(Integer restaurantId);

}
