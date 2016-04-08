package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface UserService {

    public Collection<User> findAll();

    public boolean alreadyExists(String email);
}
