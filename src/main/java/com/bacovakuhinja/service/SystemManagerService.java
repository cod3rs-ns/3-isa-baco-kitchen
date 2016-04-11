package com.bacovakuhinja.service;

import com.bacovakuhinja.model.SystemManager;
import java.util.Collection;

public interface SystemManagerService {

    public Collection<SystemManager> findAll();

    public SystemManager findOne(Integer smId);

    public SystemManager create(SystemManager sm);

    public SystemManager update(SystemManager sm);

    public void delete(Integer smId);

}
