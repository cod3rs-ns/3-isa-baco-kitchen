package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.SendProvidersMail;
import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.service.OfferRequestService;
import com.bacovakuhinja.service.ProviderResponseService;
import com.bacovakuhinja.service.RestaurantProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class OfferRequestController {

    @Autowired
    private OfferRequestService offerRequestService;

    @Autowired
    private ProviderResponseService providerResponseService;

    @Autowired
    private RestaurantProviderService restaurantProviderService;

    @RequestMapping(
            value = "/api/offers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <OfferRequest>> getOfferRequests() {
        Collection <OfferRequest> offers = offerRequestService.findAll();
        return new ResponseEntity <Collection <OfferRequest>>(offers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/offers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <OfferRequest> getOfferRequest(@PathVariable("id") Integer id) {
        OfferRequest offer = offerRequestService.findOne(id);
        return new ResponseEntity <OfferRequest>(offer, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/offers",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <OfferRequest> createOfferRequest(@RequestBody OfferRequest offerRequest) {
        OfferRequest created = offerRequestService.create(offerRequest);
        return new ResponseEntity <OfferRequest>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/offers",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <OfferRequest> updateOfferRequest(@RequestBody OfferRequest offerRequest) {
        OfferRequest updateOffer = offerRequestService.update(offerRequest);
        if (updateOffer == null) {
            return new ResponseEntity <OfferRequest>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <OfferRequest>(updateOffer, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/offers/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <OfferRequest> deleteOfferRequest(@PathVariable("id") Integer id) {
        offerRequestService.delete(id);
        return new ResponseEntity <OfferRequest>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/offers/r={rst_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <OfferRequest>> findByRestaurant(@PathVariable("rst_id") Integer id) {
        Collection <OfferRequest> offerRequests = offerRequestService.findAllByRestaurant(id);
        return new ResponseEntity <Collection <OfferRequest>>(offerRequests, HttpStatus.OK);
    }


    @SendProvidersMail
    @RequestMapping(
            value = "api/offers_a/offer={offer_id}&accept={response_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <OfferRequest> acceptOfferResponse(@PathVariable("offer_id") Integer offerId, @PathVariable("response_id") Integer responseId) {
        OfferRequest offer = offerRequestService.findOne(offerId);
        Collection <ProviderResponse> responses = providerResponseService.findAllByOffer(offer);
        for (ProviderResponse response : responses) {
            if (response.getResponseId() == responseId) {
                response.setStatus("accepted");
                offer.setAcceptedResponse(response.getResponseId());
                offer.setStatus("closed");

            } else {
                response.setStatus("rejected");

            }
        }
        providerResponseService.updateAll(responses);
        offer = offerRequestService.update(offer);
        return new ResponseEntity <OfferRequest>(offer, HttpStatus.OK);
    }

    @SendProvidersMail
    @RequestMapping(
            value = "api/offers_n/offer={offer_id}&reject={response_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <OfferRequest> rejectOfferResponse(@PathVariable("offer_id") Integer offerId, @PathVariable("response_id") Integer responseId) {
        OfferRequest offer = offerRequestService.findOne(offerId);
        ProviderResponse response = providerResponseService.findOne(responseId);
        response.setStatus("rejected");
        providerResponseService.update(response);
        return new ResponseEntity <OfferRequest>(offer, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/offers/newp={provider_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <OfferRequest>> getNewOffersForProvider(@PathVariable("provider_id") Integer id) {
        Collection <OfferRequest> toRemove = new ArrayList <OfferRequest>();
        Collection <OfferRequest> allOfferRequests = offerRequestService.findAll();
        Collection <ProviderResponse> responses = providerResponseService.findAllByProvider(restaurantProviderService.findOne(id));
        for (ProviderResponse response: responses) {
            for(OfferRequest offer: allOfferRequests){
                if(response.getOffer().getOfferId() == offer.getOfferId()){
                    toRemove.add(offer);
                    break;
                }
            }
        }
        System.out.println(toRemove.size());
        System.out.println(allOfferRequests.size());
        for (OfferRequest or: toRemove){
            allOfferRequests.remove(or);
        }
        System.out.println(allOfferRequests.size());
        return new ResponseEntity <Collection <OfferRequest>>(allOfferRequests, HttpStatus.OK);
    }

}
