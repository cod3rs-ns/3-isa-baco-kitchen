package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.repository.GuestRepository;
import com.bacovakuhinja.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    public Collection<Guest> getFriends() {
        return null;
    }

    @Override
    public Collection<Guest> getFriendRequests() {
        return null;
    }

    @Override
    public Guest getGuest(Integer id) {
        return guestRepository.findOne(id);
    }

    @Override
    public Collection<User> getUsers(String query) {
        Collection<User> users = new ArrayList<User>();
        for (Guest guest : guestRepository.findAll()) {
            if (guest.getFirstName().toLowerCase().contains(query) || guest.getLastName().toLowerCase().contains(query)) {
                users.add(guest);
                if (users.size() >= 5) break;
            }
        }

        return users;
    }

    @Override
    public void create(User user, String additionalInfo) {
        // FIXME
        Guest guest = new Guest();

        guest.setEmail(user.getEmail());
        guest.setFirstName(user.getFirstName());
        guest.setLastName(user.getLastName());
        guest.setImage(user.getImage());
        guest.setPassword(user.getPassword());
        guest.setType(user.getType());
        guest.setVerified(user.getVerified());

        guest.setGuestId(user.getUserId());
        guest.setGuestInfo(additionalInfo);

        guestRepository.save(guest);
    }
}
