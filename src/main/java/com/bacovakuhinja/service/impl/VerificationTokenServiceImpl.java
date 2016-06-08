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
        return verificationTokenRepository.findByToken(tokenValue);
    }

    @Override
    public VerificationToken getByUserId(Integer id) {
        return verificationTokenRepository.findByUser_userId(id);
    }

    @Override
    public VerificationToken update(VerificationToken token) {
        VerificationToken tokenPersistent = verificationTokenRepository.findOne(token.getId());

        if (tokenPersistent == null)
            return null;

        return verificationTokenRepository.save(token);
    }

    @Override
    public void delete(VerificationToken token) {
        verificationTokenRepository.delete(token.getId());
    }
}
