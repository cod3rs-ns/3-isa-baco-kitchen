package com.bacovakuhinja.service.impl;


import com.bacovakuhinja.model.Friendship;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.FriendshipRepository;
import com.bacovakuhinja.repository.GuestRepository;
import com.bacovakuhinja.service.FriendshipService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Collection<User> getFriendshipsRequestByGuestID(Integer id) {
        return friendshipRepository.findFriendshipRequests(Constants.FriendshipStatus.WAITING, id);
    }

    @Override
    public Collection<User> getFriendsByGuestID(Integer id) {

        Collection<User> rec = friendshipRepository.findFriendsReceivers(Constants.FriendshipStatus.ACCEPTED, id);
        Collection<User> sen = friendshipRepository.findFriendsSenders(Constants.FriendshipStatus.ACCEPTED, id);
        rec.addAll(sen);

        return rec;
    }

    @Override
    public void acceptRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(Constants.FriendshipStatus.WAITING, senderId, receiverId);

        if (friendship != null) {
            friendship.setStatus(Constants.FriendshipStatus.ACCEPTED);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    public void rejectRequest(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(Constants.FriendshipStatus.WAITING, senderId, receiverId);

        if (friendship != null) {
            friendship.setStatus(Constants.FriendshipStatus.REJECTED);
            friendshipRepository.save(friendship);
        }
    }

    @Override
    public void addFriend(Integer senderId, Integer receiverId) {
        Friendship fs = new Friendship();
        fs.setSender(guestRepository.findOne(senderId));
        fs.setReceiver(guestRepository.findOne(receiverId));
        fs.setStatus(Constants.FriendshipStatus.WAITING);

        friendshipRepository.save(fs);
    }

    @Override
    public void removeFriend(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(Constants.FriendshipStatus.ACCEPTED, senderId, receiverId);

        if (friendship != null) {
            friendshipRepository.delete(friendship);
        }
    }

    @Override
    public boolean areWeFriends(Integer senderId, Integer receiverId) {
        Friendship friendship = friendshipRepository.getFriendship(Constants.FriendshipStatus.ACCEPTED, senderId, receiverId);
        return (friendship == null) ? false : true;
    }
}