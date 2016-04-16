package com.bacovakuhinja.service;

import com.bacovakuhinja.model.VerificationToken;

public interface VerificationTokenService {

    public VerificationToken create(VerificationToken token);

    public VerificationToken get(String tokenValue);

    public void delete(VerificationToken token);
}
