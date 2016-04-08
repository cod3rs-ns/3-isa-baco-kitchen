package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends
        JpaRepository<User, Integer> {
}
