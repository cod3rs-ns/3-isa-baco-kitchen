package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface FriendshipService {

    public Collection<User> getFriendshipsRequestByGuestID(Integer id);

    public Collection<User> getFriendsByGuestID(Integer id);

    public void acceptRequest(Integer senderId, Integer receiverId);

    public void rejectRequest(Integer senderId, Integer receiverId);

    public void addFriend(Integer senderId, Integer receiverId);

    public void removeFriend(Integer senderId, Integer receiverId);

    public boolean areWeFriends(Integer senderId, Integer receiverId);
}
