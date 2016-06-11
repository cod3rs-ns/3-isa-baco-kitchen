package com.bacovakuhinja.repository;


import com.bacovakuhinja.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends
        JpaRepository<VerificationToken, Integer> {

        VerificationToken findByToken(String tokenValue);

        VerificationToken findByUser_userId(Integer userId);
}
