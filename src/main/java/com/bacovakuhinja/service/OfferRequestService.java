package com.bacovakuhinja.service;

import com.bacovakuhinja.model.OfferRequest;

import java.util.Collection;

public interface OfferRequestService {

    public Collection<OfferRequest> findAll();

    public OfferRequest findOne(Integer id);

    public OfferRequest create(OfferRequest offerRequest);

    public OfferRequest update(OfferRequest offerRequest);

    public void delete(Integer id);

    public Collection <OfferRequest> findAllByRestaurant(Integer restaurantId);

}
