package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Employee;
import com.bacovakuhinja.model.Waiter;
import com.bacovakuhinja.repository.WaiterRepository;
import com.bacovakuhinja.service.EmployeeService;
import com.bacovakuhinja.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WaiterServiceImpl implements WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    @Override
    public Collection<Waiter> findAll() {
        return waiterRepository.findAll();
    }

    @Override
    public Waiter findOne(Integer eId) {
        return waiterRepository.findOne(eId);
    }

    @Override
    public Waiter create(Waiter e) {
        return waiterRepository.save(e);
    }

    @Override
    public Waiter update(Waiter e) {
        Waiter waiterPersistent = waiterRepository.findOne(e.getUserId());
        if (waiterPersistent == null) return null;
        waiterPersistent.update(e);
        return waiterRepository.save(waiterPersistent);
    }

    @Override
    public void delete(Integer eId) {
        waiterRepository.delete(eId);
    }
}
