package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.repository.OfferRequestRepository;
import com.bacovakuhinja.service.OfferRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OfferRequestServiceImpl implements OfferRequestService {

    @Autowired
    private OfferRequestRepository offerRequestRepository;

    @Override
    public Collection <OfferRequest> findAll() {
        return offerRequestRepository.findAll();
    }

    @Override
    public OfferRequest findOne(Integer id) {
        return offerRequestRepository.findOne(id);
    }

    @Override
    public OfferRequest create(OfferRequest offerRequest) {
        return offerRequestRepository.save(offerRequest);
    }

    @Override
    public OfferRequest update(OfferRequest offerRequest) {
        OfferRequest persistentOffer = offerRequestRepository.findOne(offerRequest.getOfferId());
        if (persistentOffer == null) return null;
        return offerRequestRepository.save(offerRequest);
    }

    @Override
    public void delete(Integer id) {
        offerRequestRepository.delete(id);
    }

    @Override
    public Collection <OfferRequest> findAllByRestaurant(Integer restaurantId) {
        return offerRequestRepository.findByRestaurantId(restaurantId);
    }

}
