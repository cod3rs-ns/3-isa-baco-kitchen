package com.bacovakuhinja.service;


import com.bacovakuhinja.model.ClientOrder;

import java.util.Collection;
import java.util.List;

public interface ClientOrderService {
    public Collection<ClientOrder> findAll();

    public ClientOrder findOne(Integer eId);

    public ClientOrder create(ClientOrder e);

    public ClientOrder update(ClientOrder e);

    public void delete(Integer eId);
}
