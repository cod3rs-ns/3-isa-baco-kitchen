package com.bacovakuhinja.service;

import com.bacovakuhinja.model.SystemManager;
import java.util.Collection;

public interface SystemManagerService {

    Collection<SystemManager> findAll();

    SystemManager findOne(Integer smId);

    SystemManager create(SystemManager sm);

    SystemManager update(SystemManager sm);

    void delete(Integer smId);

}
