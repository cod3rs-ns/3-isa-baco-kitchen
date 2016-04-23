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
        Collection<User> requests = new ArrayList<User>();

        for (Friendship fs : friendshipRepository.findAll()) {
            if (fs.getReceiver().getUserId() == id && fs.getStatus().equals(WAITING)) {
                requests.add(fs.getSender());
            }
        }

        return requests;
    }

    @Override
    public Collection<User> getFriendsByGuestID(Integer id) {
        Collection<User> friends = new ArrayList<User>();

        for (Friendship fs : friendshipRepository.findAll()) {
            if (fs.getReceiver().getUserId() == id && fs.getStatus().equals(ACCEPTED)) {
                friends.add(fs.getSender());
            }
            else if (fs.getSender().getUserId() == id && fs.getStatus().equals(ACCEPTED)) {
                friends.add(fs.getReceiver());
            }
        }

        return friends;
    }

    @Override
    public void acceptRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = null;
        for (Friendship fs : friendshipRepository.findAll()) {
            if (fs.getStatus().equals(WAITING) && fs.getSender().getGuestId() == senderId && fs.getReceiver().getGuestId() == receiverId) {
                friendship = fs;
                break;
            }
        }

        if (friendship != null) {
            friendship.setStatus(ACCEPTED);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    public void rejectRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = null;
        for (Friendship fs : friendshipRepository.findAll()) {
            if (fs.getStatus().equals(WAITING) && fs.getSender().getGuestId() == senderId && fs.getReceiver().getGuestId() == receiverId) {
                friendship = fs;
                break;
            }
        }

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
        Friendship friendship = null;
        for (Friendship fs : friendshipRepository.findAll()) {
            if (fs.getSender().getGuestId() == senderId && fs.getReceiver().getGuestId() == receiverId) {
                friendship = fs;
                break;
            }
            else if (fs.getSender().getGuestId() == receiverId && fs.getReceiver().getGuestId() == senderId) {
                friendship = fs;
                break;
            }
        }

        if (friendship != null) {
            friendshipRepository.delete(friendship);
        }
    }

    @Override
    public boolean areWeFriends(Integer senderId, Integer receiverId) {
        for (User g : getFriendsByGuestID(senderId)) {
            if (g.getUserId() == receiverId) {
                return true;
            }
        }
        return false;
    }


}
