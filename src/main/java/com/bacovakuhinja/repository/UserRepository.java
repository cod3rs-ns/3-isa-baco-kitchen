package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends
        JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE (u.firstName LIKE %?1% OR u.lastName LIKE %?1%) AND u.type = 'guest'")
    Collection<User> findGuestsByQuery(String query);
}
