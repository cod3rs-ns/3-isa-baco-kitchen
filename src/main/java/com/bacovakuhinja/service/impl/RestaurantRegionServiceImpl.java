package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.RestaurantRegion;
import com.bacovakuhinja.repository.RestaurantRegionRepository;
import com.bacovakuhinja.service.RestaurantRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RestaurantRegionServiceImpl implements RestaurantRegionService {

    @Autowired
    private RestaurantRegionRepository regionRepository;

    @Override
    public Collection<RestaurantRegion> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public RestaurantRegion findOne(Integer id) {
        return regionRepository.findOne(id);
    }

    @Override
    public RestaurantRegion create(RestaurantRegion restaurantRegion) {
        return regionRepository.save(restaurantRegion);
    }

    @Override
    public RestaurantRegion update(RestaurantRegion restaurantRegion) {
        RestaurantRegion regionPersistent = findOne(restaurantRegion.getRegionId());
        if (regionPersistent == null) return null;
        return regionRepository.save(restaurantRegion);
    }

    @Override
    public void delete(Integer id) {
        regionRepository.delete(id);
    }

    @Override
    public Collection <RestaurantRegion> findAllByRestaurant(Integer restaurantId) {
        Collection <RestaurantRegion> allRegions = regionRepository.findAll();
        Collection <RestaurantRegion> restaurantRegions = new ArrayList<RestaurantRegion>();
        for (RestaurantRegion region : allRegions) {
            if (region.getRestaurant().getRestaurantId() == restaurantId) {
                restaurantRegions.add(region);
            }
        }
        return restaurantRegions;
    }
}
