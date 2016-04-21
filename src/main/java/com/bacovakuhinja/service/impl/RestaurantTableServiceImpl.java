package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.RestaurantTable;
import com.bacovakuhinja.repository.RestaurantTableRepository;
import com.bacovakuhinja.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Override
    public Collection <RestaurantTable> findAll() {
        return tableRepository.findAll();
    }

    @Override
    public RestaurantTable findOne(Integer id) {
        return tableRepository.findOne(id);
    }

    @Override
    public RestaurantTable create(RestaurantTable restaurantTable) {
        return tableRepository.save(restaurantTable);
    }

    @Override
    public RestaurantTable update(RestaurantTable restaurantTable) {
        RestaurantTable tablePersistent = findOne(restaurantTable.getTableId());
        if (tablePersistent == null) return null;
        return tableRepository.save(restaurantTable);
    }

    @Override
    public void delete(Integer id) {
        tableRepository.delete(id);
    }

    @Override
    public Collection <RestaurantTable> findAllByRestaurant(Integer restaurantId) {
        Collection <RestaurantTable> allTables = tableRepository.findAll();
        Collection <RestaurantTable> restaurantTables = new ArrayList <RestaurantTable>();
        for (RestaurantTable table : allTables) {
            if (table.getRegion().getRestaurant().getRestaurantId() == restaurantId) {
                restaurantTables.add(table);
            }
        }
        return restaurantTables;
    }

    @Override
    public Collection <RestaurantTable> findAllByRegion(Integer regionId) {
        return null;
    }

}
