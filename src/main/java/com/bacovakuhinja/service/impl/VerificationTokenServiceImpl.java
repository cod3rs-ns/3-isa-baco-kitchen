package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.repository.VerificationTokenRepository;
import com.bacovakuhinja.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Override
    public VerificationToken create(VerificationToken token) {
        return tokenRepository.save(token);
    }

    @Override
    public VerificationToken get(String tokenValue) {
        return tokenRepository.findByToken(tokenValue);
    }

    @Override
    public VerificationToken getByUserId(Integer id) {
        return tokenRepository.findByUser_userId(id);
    }

    @Override
    public VerificationToken update(VerificationToken token) {
        VerificationToken tokenPersistent = tokenRepository.findOne(token.getId());

        if (tokenPersistent == null)
            return null;

        return tokenRepository.save(token);
    }

    @Override
    public void delete(VerificationToken token) {
        tokenRepository.delete(token.getId());
    }
}
