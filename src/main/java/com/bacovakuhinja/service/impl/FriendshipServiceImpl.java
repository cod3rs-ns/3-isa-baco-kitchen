package com.bacovakuhinja.service.impl;


import com.bacovakuhinja.model.Friendship;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.FriendshipRepository;
import com.bacovakuhinja.repository.GuestRepository;
import com.bacovakuhinja.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static final String WAITING  = "waiting";

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Collection<User> getFriendshipsRequestByGuestID(Integer id) {
        return friendshipRepository.findFriendshipRequests(WAITING, id);
    }

    @Override
    public Collection<User> getFriendsByGuestID(Integer id) {

        Collection<User> rec = friendshipRepository.findFriendsReceivers(ACCEPTED, id);
        Collection<User> sen = friendshipRepository.findFriendsSenders(ACCEPTED, id);
        rec.addAll(sen);

        return rec;
    }

    @Override
    public void acceptRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(WAITING, senderId, receiverId);

        if (friendship != null) {
            friendship.setStatus(ACCEPTED);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    public void rejectRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(WAITING, senderId, receiverId);

        if (friendship != null) {
            friendship.setStatus(REJECTED);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    public void addFriend(Integer senderId, Integer receiverId) {
        Friendship fs = new Friendship();
        fs.setSender(guestRepository.findOne(senderId));
        fs.setReceiver(guestRepository.findOne(receiverId));
        fs.setStatus(WAITING);

        friendshipRepository.save(fs);
    }

    @Override
    public void removeFriend(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(ACCEPTED, senderId, receiverId);

        if (friendship != null) {
            friendshipRepository.delete(friendship);
        }
    }

    @Override
    public boolean areWeFriends(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(ACCEPTED, senderId, receiverId);
        return (friendship == null) ? false : true;
    }
}