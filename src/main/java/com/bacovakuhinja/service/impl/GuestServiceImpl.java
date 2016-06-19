package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.GuestRepository;
import com.bacovakuhinja.repository.UserRepository;
import com.bacovakuhinja.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Guest getGuest(Integer id) {
        return guestRepository.findOne(id);
    }

    @Override
    public Collection<User> getUsers(String query) {
        // FIXME Only top N results
        ArrayList<User> result = (ArrayList<User>) userRepository.findGuestsByQuery(query);
        return result;
    }

    @Override
    public User create(Guest user) {
        return guestRepository.save(user);
    }
}
