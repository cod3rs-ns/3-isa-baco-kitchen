package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface GuestService {

    public Collection<Guest> getFriends();

    public Collection<Guest> getFriendRequests();

    public Guest getGuest(Integer id);

    public Collection<User> getUsers(String query);
}
