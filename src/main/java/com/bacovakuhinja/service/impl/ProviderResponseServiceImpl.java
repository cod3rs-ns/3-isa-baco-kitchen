package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.model.Restaurant;
import com.bacovakuhinja.model.RestaurantProvider;
import com.bacovakuhinja.repository.ProviderResponseRepository;
import com.bacovakuhinja.service.ProviderResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProviderResponseServiceImpl implements ProviderResponseService {

    @Autowired
    private ProviderResponseRepository responseRepository;

    @Override
    public Collection <ProviderResponse> findAll() {
        return responseRepository.findAll();
    }

    @Override
    public ProviderResponse findOne(Integer id) {
        return responseRepository.findOne(id);
    }

    @Override
    public ProviderResponse create(ProviderResponse providerResponse) {
        return responseRepository.save(providerResponse);
    }

    @Override
    public ProviderResponse update(ProviderResponse providerResponse) {
        ProviderResponse response = responseRepository.findOne(providerResponse.getResponseId());
        if (response == null) return null;
        return responseRepository.save(providerResponse);
    }

    @Override
    public void delete(Integer id) {
        responseRepository.delete(id);
    }

    @Override
    public Collection <ProviderResponse> findAllByProvider(RestaurantProvider provider) {
        return responseRepository.findByProvider(provider);
    }

    @Override
    public Collection <ProviderResponse> findAllByOffer(OfferRequest offerRequest) {
       return responseRepository.findByOffer(offerRequest);
    }

    @Override
    public Collection <ProviderResponse> updateAll(Collection <ProviderResponse> responses) {
        return responseRepository.save(responses);
    }
}
