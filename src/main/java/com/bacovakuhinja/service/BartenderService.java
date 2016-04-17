package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Bartender;

import java.util.Collection;

public interface BartenderService {

    public Collection<Bartender> findAll();

    public Bartender findOne(Integer eId);

    public Bartender create(Bartender e);

    public Bartender update(Bartender e);

    public void delete(Integer eId);

}
