package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.SystemManager;
import com.bacovakuhinja.repository.SystemManagerRepository;
import com.bacovakuhinja.service.SystemManagerService;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemManagerServiceImpl implements SystemManagerService{
    @Autowired
    private SystemManagerRepository smRepository;


    @Override
    public Collection<SystemManager> findAll() {
        return smRepository.findAll();
    }

    @Override
    public SystemManager findOne(Integer smId) {
        return smRepository.findOne(smId);
    }

    @Override
    public SystemManager create(SystemManager sm) {
        return smRepository.save(sm);
    }

    @Override
    public SystemManager update(SystemManager sm) {
        SystemManager smPersistent = findOne(sm.getUserId());
        if(smPersistent == null) return null;
        return smRepository.save(sm);
    }

    @Override
    public void delete(Integer rmId) {
        smRepository.delete(rmId);
    }
}
