package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.RestaurantProvider;
import com.bacovakuhinja.repository.RestaurantProviderRepository;
import com.bacovakuhinja.service.RestaurantProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RestaurantProviderServiceImpl implements RestaurantProviderService {

    @Autowired
    private RestaurantProviderRepository providerRepository;

    @Override
    public Collection<RestaurantProvider> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public RestaurantProvider create(RestaurantProvider provider) {
        return providerRepository.save(provider);
    }
}
