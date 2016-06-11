package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    boolean alreadyExists(String email);

    User create(User user);

    User findOne(String email);

    User findOneByEmailAndPassword(String email, String password);

    User update(User user);
}
