package com.bacovakuhinja.service.impl;


import com.bacovakuhinja.model.Friendship;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.FriendshipRepository;
import com.bacovakuhinja.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public Collection<User> getFriendshipsRequestByGuestID(Integer id) {

        System.out.println("Called service");

        Collection<User> requests = new ArrayList<User>();

        for (Friendship fs : friendshipRepository.findAll()) {
            System.out.println(fs);
            System.out.println(fs.getReceiver());
            System.out.println(fs.getSender());
            if (fs.getReceiver().getUserId() == id) {
                requests.add(fs.getReceiver());
            }
        }

        return requests;
    }
}
