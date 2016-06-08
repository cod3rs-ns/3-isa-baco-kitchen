package com.bacovakuhinja.service;

import com.bacovakuhinja.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken create(VerificationToken token);

    VerificationToken get(String tokenValue);

    VerificationToken getByUserId(Integer id);

    VerificationToken update(VerificationToken token);

    void delete(VerificationToken token);
}
