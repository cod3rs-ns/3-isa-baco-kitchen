package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Friendship;
import com.bacovakuhinja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query ("SELECT fs.sender FROM Friendship fs WHERE fs.status = ?1 AND fs.receiver.guestId = ?2")
    Collection<User> findFriendshipRequests(String status, Integer receiverId);

    @Query ("SELECT fs.sender FROM Friendship fs WHERE fs.status = ?1 AND fs.receiver.guestId = ?2")
    Collection<User> findFriendsSenders(String status, Integer userId);

    @Query ("SELECT fs.receiver FROM Friendship fs WHERE fs.status = ?1 AND fs.sender.guestId = ?2")
    Collection<User> findFriendsReceivers(String status, Integer userId);

    @Query ("SELECT fs FROM Friendship fs WHERE fs.status = ?1 AND ((fs.sender.guestId = ?2 AND fs.receiver.guestId = ?3) OR (fs.sender.guestId = ?3 AND fs.receiver.guestId = ?2))")
    Friendship getFriendship(String status, Integer receiverId, Integer senderId);
}
