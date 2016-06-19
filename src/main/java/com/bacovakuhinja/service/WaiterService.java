package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Waiter;

import java.util.Collection;

public interface WaiterService {

    Collection<Waiter> findAll();

    Waiter findOne(Integer wId);

    Waiter create(Waiter w);

    Waiter update(Waiter w);

    void delete(Integer wId);

}
