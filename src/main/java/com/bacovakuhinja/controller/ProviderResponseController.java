package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.service.ProviderResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ProviderResponseController {

    @Autowired
    private ProviderResponseService responseService;

    @RequestMapping(
            value = "/api/provider_responses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <ProviderResponse>> getProviderResponses() {
        Collection <ProviderResponse> responses = responseService.findAll();
        return new ResponseEntity <Collection <ProviderResponse>>(responses, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/provider_responses/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ProviderResponse> getProviderResponse(@PathVariable("id") Integer id) {
        ProviderResponse response = responseService.findOne(id);
        return new ResponseEntity <ProviderResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/provider_responses/",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ProviderResponse> createProviderResponse(@RequestBody ProviderResponse providerResponse) {
        ProviderResponse created = responseService.create(providerResponse);
        return new ResponseEntity <ProviderResponse>(created, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/provider_responses",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <ProviderResponse> updateProviderResponse(@RequestBody ProviderResponse provider) {
        ProviderResponse updatedResponse = responseService.update(provider);
        if (updatedResponse == null) {
            return new ResponseEntity <ProviderResponse>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity <ProviderResponse>(updatedResponse, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/provider_responses/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity <ProviderResponse> deleteProviderResponse(@PathVariable("id") Integer id) {
        responseService.delete(id);
        return new ResponseEntity <ProviderResponse>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            value = "api/provider_responses/o={offer_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <ProviderResponse>> getResponsesByOffer(@PathVariable("offer_id") Integer id) {
        Collection <ProviderResponse> providerResponses = responseService.findAllByOffer(id);
        return new ResponseEntity <Collection <ProviderResponse>>(providerResponses, HttpStatus.OK);
    }

    @RequestMapping(
            value = "api/provider_responses/p={provider_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection <ProviderResponse>> getResponsesByProvider(@PathVariable("provider_id") Integer id) {
        Collection <ProviderResponse> providerResponses = responseService.findAllByProvider(id);
        return new ResponseEntity <Collection <ProviderResponse>>(providerResponses, HttpStatus.OK);
    }

}
