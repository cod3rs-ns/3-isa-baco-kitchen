package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.SendProvidersMail;
import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.service.OfferRequestService;
import com.bacovakuhinja.service.ProviderResponseService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OfferRequestController {

    @Autowired
    private OfferRequestService offerRequestService;

    @Autowired
    private ProviderResponseService providerResponseService;

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
            if (response.getResponseId().equals(responseId)) {
                response.setStatus(Constants.ResponseStatus.ACCEPTED);
                offer.setAcceptedResponse(response.getResponseId());
                offer.setStatus(Constants.OfferStatus.CLOSED);

            } else {
                response.setStatus(Constants.ResponseStatus.REJECTED);
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
        response.setStatus(Constants.ResponseStatus.REJECTED);
        providerResponseService.update(response);
        return new ResponseEntity <OfferRequest>(offer, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/offers/new/p={provider_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <OfferRequest>> getNewOffersForProvider(@PathVariable("provider_id") Integer providerId) {
        Collection <OfferRequest> allOfferRequests = offerRequestService.findNewOfferRequestsForProviderId(providerId);
        return new ResponseEntity <Collection <OfferRequest>>(allOfferRequests, HttpStatus.OK);
    }

}
