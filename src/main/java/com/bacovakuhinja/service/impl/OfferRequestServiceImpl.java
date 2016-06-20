package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.repository.OfferRequestRepository;
import com.bacovakuhinja.service.OfferRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

    @Override
    public Collection <OfferRequest> findNonExpiredOffers(Date date) {
        return offerRequestRepository.findNonExpiredOffers(date);
    }

    @Override
    public Collection <OfferRequest> findAppliedOfferRequestsByProvider(Integer providerId) {
        return offerRequestRepository.findAppliedOfferRequestsByProvider(providerId);
    }

    @Override
    public Collection <OfferRequest> findNewOfferRequestsForProviderId(Integer providerId) {
        Collection <OfferRequest> nonExpired = findNonExpiredOffers(new Date());
        Collection <OfferRequest> appliedFor = findAppliedOfferRequestsByProvider(providerId);
        Collection <OfferRequest> toIgnore = new ArrayList <OfferRequest>();
        for (OfferRequest or : nonExpired) {
            for (OfferRequest or2 : appliedFor) {
                if (or.getOfferId().equals(or2.getOfferId())) {
                    toIgnore.add(or);
                }
            }
        }

        for (OfferRequest or : toIgnore) {
            nonExpired.remove(or);
        }
        return nonExpired;
    }

}
