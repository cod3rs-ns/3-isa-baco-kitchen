package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Cook;
import com.bacovakuhinja.repository.CookRepository;
import com.bacovakuhinja.service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CookServiceImpl implements CookService {

    @Autowired
    private CookRepository cookRepository;

    @Override
    public Collection<Cook> findAll() {
        return cookRepository.findAll();
    }

    @Override
    public Cook findOne(Integer eId) {
        return cookRepository.findOne(eId);
    }

    @Override
    public Cook create(Cook e) {
        return cookRepository.save(e);
    }

    @Override
    public Cook update(Cook e) {
        Cook cookPersistent = cookRepository.findOne(e.getUserId());
        if (cookPersistent == null) return null;
        cookPersistent.update(e);
        return cookRepository.save(cookPersistent);
    }

    @Override
    public void delete(Integer eId) {
        cookRepository.delete(eId);
    }
}
