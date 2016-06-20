package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.OfferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;

public interface OfferRequestRepository extends JpaRepository <OfferRequest, Integer> {

    Collection <OfferRequest> findByRestaurantId(Integer restaurantId);

    @Query("SELECT ofr FROM OfferRequest ofr where (ofr.deadline >= ?1)")
    Collection <OfferRequest> findNonExpiredOffers(Date date);

    @Query("SELECT ofr FROM OfferRequest ofr, ProviderResponse pr WHERE (pr.provider.userId = ?1) AND (ofr.offerId = pr.offer.offerId)")
    Collection <OfferRequest> findAppliedOfferRequestsByProvider(Integer providerId);
}
