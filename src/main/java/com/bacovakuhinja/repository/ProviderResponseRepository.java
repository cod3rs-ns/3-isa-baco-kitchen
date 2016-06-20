package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.OfferRequest;
import com.bacovakuhinja.model.ProviderResponse;
import com.bacovakuhinja.model.RestaurantProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProviderResponseRepository extends JpaRepository <ProviderResponse, Integer> {

    Collection <ProviderResponse> findByOffer(OfferRequest offerRequest);

    Collection <ProviderResponse> findByProvider(RestaurantProvider provider);
}
