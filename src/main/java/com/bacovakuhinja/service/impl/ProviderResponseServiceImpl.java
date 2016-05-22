package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.ProviderResponse;
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
    public Collection <ProviderResponse> findAllByProvider(Integer providerId) {
        return responseRepository.findByProviderId(providerId);
    }

    @Override
    public Collection <ProviderResponse> findAllByOffer(Integer offerId) {
       return responseRepository.findByOfferId(offerId);
    }
}
