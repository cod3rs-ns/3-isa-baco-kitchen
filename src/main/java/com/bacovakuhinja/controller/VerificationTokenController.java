package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class VerificationTokenController {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    // Registration Verification
    @RequestMapping(
            value    = "api/registrationConfirm",
            method   = RequestMethod.GET
    )
    public ResponseEntity<?> registrationConfirm(@RequestParam(value="token") String token) {

        VerificationToken verificationToken = verificationTokenService.get(token);

        // Wrong token
        if (verificationToken == null)
            return null;

        Date currentTime = new Date();

        if (currentTime.after(verificationToken.getExpiryDate())) {
            // Verification Token expired
            return null;
        }

        User user = verificationToken.getUser();
        user.setVerified("verified");

        // Update user
        userService.update(user);
        // Delete token
        verificationTokenService.delete(verificationToken);

        return null;
    }

}
