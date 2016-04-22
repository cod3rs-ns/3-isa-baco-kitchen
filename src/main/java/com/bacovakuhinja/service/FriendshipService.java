package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface FriendshipService {

    public Collection<User> getFriendshipsRequestByGuestID(Integer id);
}
