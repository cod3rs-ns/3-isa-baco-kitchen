package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.repository.GuestRepository;
import com.bacovakuhinja.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Collection<Guest> getFriends() {
        return null;
    }

    @Override
    public Collection<Guest> getFriendRequests() {
        return null;
    }
}
