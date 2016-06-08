package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface GuestService {

    Guest getGuest(Integer id);

    Collection<User> getUsers(String query);

    User create(Guest user);
}
