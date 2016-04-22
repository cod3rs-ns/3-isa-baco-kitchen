package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Guest;

import java.util.Collection;

public interface GuestService {

    public Collection<Guest> getFriends();

    public Collection<Guest> getFriendRequests();

    public Guest getGuest(Integer id);
}
