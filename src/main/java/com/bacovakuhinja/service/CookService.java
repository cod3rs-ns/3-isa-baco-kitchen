package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Cook;

import java.util.Collection;

public interface CookService {

    public Collection<Cook> findAll();

    public Cook findOne(Integer eId);

    public Cook create(Cook e);

    public Cook update(Cook e);

    public void delete(Integer eId);

}
