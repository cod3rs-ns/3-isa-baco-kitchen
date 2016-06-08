package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface UserService {

    public Collection<User> findAll();

    public boolean alreadyExists(String email);

    public User create(User user);

    public User findOne(String email);

    public User findOneByEmailAndPassword(String email, String password);

    public User update(User user);
}
