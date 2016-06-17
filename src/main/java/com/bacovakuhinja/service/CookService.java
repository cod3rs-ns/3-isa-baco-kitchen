package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Cook;

import java.util.Collection;

public interface CookService {

    Collection<Cook> findAll();

    Cook findOne(Integer eId);

    Cook create(Cook e);

    Cook update(Cook e);

    void delete(Integer eId);

}
