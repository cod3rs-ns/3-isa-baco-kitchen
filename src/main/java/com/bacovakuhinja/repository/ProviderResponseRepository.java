package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.ProviderResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProviderResponseRepository extends JpaRepository <ProviderResponse, Integer> {

    Collection <ProviderResponse> findByOfferId(Integer offerId);
    Collection <ProviderResponse> findByProviderId(Integer providerId);
}
