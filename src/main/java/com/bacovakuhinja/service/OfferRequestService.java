package com.bacovakuhinja.service;

import com.bacovakuhinja.model.OfferRequest;

import java.util.Collection;
import java.util.Date;

public interface OfferRequestService {

    Collection <OfferRequest> findAll();

    OfferRequest findOne(Integer id);

    OfferRequest create(OfferRequest offerRequest);

    OfferRequest update(OfferRequest offerRequest);

    void delete(Integer id);

    Collection <OfferRequest> findAllByRestaurant(Integer restaurantId);

    Collection <OfferRequest> findNonExpiredOffers(Date date);

    Collection <OfferRequest> findAppliedOfferRequestsByProvider(Integer providerId);

    Collection <OfferRequest> findNewOfferRequestsForProviderId(Integer providerId);

}
