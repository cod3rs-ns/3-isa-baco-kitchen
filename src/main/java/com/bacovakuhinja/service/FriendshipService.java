package com.bacovakuhinja.service;

import com.bacovakuhinja.model.User;

import java.util.Collection;

public interface FriendshipService {

    Collection<User> getFriendshipsRequestByGuestID(Integer id);

    Collection<User> getFriendsByGuestID(Integer id);

    void acceptRequest(Integer senderId, Integer receiverId);

    void rejectRequest(Integer senderId, Integer receiverId);

    void addFriend(Integer senderId, Integer receiverId);

    void removeFriend(Integer senderId, Integer receiverId);

    boolean areWeFriends(Integer senderId, Integer receiverId);
}
