package com.bacovakuhinja.service;

import com.bacovakuhinja.model.ProviderResponse;

import java.util.Collection;

public interface ProviderResponseService {

    public Collection<ProviderResponse> findAll();

    public ProviderResponse findOne(Integer id);

    public ProviderResponse create(ProviderResponse providerResponse);

    public ProviderResponse update(ProviderResponse providerResponse);

    public void delete(Integer id);

    public Collection <ProviderResponse> findAllByProvider(Integer providerId);

    public Collection <ProviderResponse> findAllByOffer(Integer offerId);

}
