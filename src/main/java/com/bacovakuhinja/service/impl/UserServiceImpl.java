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

        for (User user: userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findOne(String email) {
        for (User user: userRepository.findAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public User findOneByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User update(User user) {
        User userPersistent = findOne(user.getEmail());

        if (userPersistent == null)
            return null;

        return userRepository.save(user);
    }
}
