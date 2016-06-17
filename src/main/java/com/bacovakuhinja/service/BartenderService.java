package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Bartender;

import java.util.Collection;

public interface BartenderService {

    Collection<Bartender> findAll();

    Bartender findOne(Integer eId);

    Bartender create(Bartender e);

    Bartender update(Bartender e);

    void delete(Integer eId);

}
