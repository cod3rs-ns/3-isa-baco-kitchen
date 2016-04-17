package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Bartender;
import com.bacovakuhinja.model.Cook;
import com.bacovakuhinja.repository.BartenderRepository;
import com.bacovakuhinja.service.BartenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BartenderServiceImpl implements BartenderService {

    @Autowired
    private BartenderRepository bartenderRepository;

    @Override
    public Collection<Bartender> findAll() {
        return bartenderRepository.findAll();
    }

    @Override
    public Bartender findOne(Integer eId) {
        return bartenderRepository.findOne(eId);
    }

    @Override
    public Bartender create(Bartender e) {
        return bartenderRepository.save(e);
    }

    @Override
    public Bartender update(Bartender e) {
        Bartender bartenderPersistent = bartenderRepository.findOne(e.getUserId());
        if (bartenderPersistent == null) return null;
        bartenderPersistent.update(e);
        return bartenderRepository.save(bartenderPersistent);
    }

    @Override
    public void delete(Integer eId) {
        bartenderRepository.delete(eId);
    }

}
