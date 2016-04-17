package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Waiter;

import java.util.Collection;

public interface WaiterService {

    public Collection<Waiter> findAll();

    public Waiter findOne(Integer wId);

    public Waiter create(Waiter w);

    public Waiter update(Waiter w);

    public void delete(Integer wId);

}
