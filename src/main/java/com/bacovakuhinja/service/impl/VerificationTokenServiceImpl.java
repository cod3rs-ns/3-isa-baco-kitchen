package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.repository.VerificationTokenRepository;
import com.bacovakuhinja.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken create(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken get(String tokenValue) {
        for (VerificationToken token : verificationTokenRepository.findAll()) {
            if (token.getToken().equals(tokenValue)) {
                return token;
            }
        }

        return null;
    }

    @Override
    public void delete(VerificationToken token) {
        verificationTokenRepository.delete(token.getId());
    }
}
