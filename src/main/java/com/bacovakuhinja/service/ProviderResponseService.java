package com.bacovakuhinja.service;

import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.model.RestaurantProvider;

import java.util.Collection;

public interface ProviderResponseService {

    Collection<ProviderResponse> findAll();

    ProviderResponse findOne(Integer id);

    ProviderResponse create(ProviderResponse providerResponse);

    ProviderResponse update(ProviderResponse providerResponse);

    void delete(Integer id);

    Collection <ProviderResponse> findAllByProvider(RestaurantProvider provider);

    Collection <ProviderResponse> findAllByOffer(OfferRequest offer);

    Collection<ProviderResponse> updateAll(Collection<ProviderResponse> responses);

}
