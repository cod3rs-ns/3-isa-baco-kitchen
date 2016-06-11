package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.UserRepository;
import com.bacovakuhinja.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean alreadyExists(String email) {
        return (userRepository.findByEmail(email) == null) ? false : true;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findOneByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User update(User user) {
        User userPersistent = userRepository.findOne(user.getUserId());

        if (userPersistent == null)
            return null;

        return userRepository.save(user);
    }
}
