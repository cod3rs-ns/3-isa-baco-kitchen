package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.service.VerificationTokenService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RestController
public class VerificationTokenController {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private UserService userService;

    // SendEmail Verification
    @RequestMapping(
            value    = "api/registration-confirm",
            method   = RequestMethod.GET
    )
    public void registrationConfirm(@RequestParam(value="token") String token, HttpServletResponse response) throws IOException {

        VerificationToken verificationToken = verificationTokenService.get(token);

        // FIXME Send Redirect
        // Wrong token
        if (verificationToken == null) {
            response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-wrong-link");
            return;
        }

        Date currentTime = new Date();

        if (currentTime.after(verificationToken.getExpiryDate())) {
            response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-expired-link");
            return;
        }

        User user = verificationToken.getUser();
        user.setVerified(Constants.Registration.STATUS_VERIFIED);

        // Update user
        userService.update(user);
        // Delete token
        verificationTokenService.delete(verificationToken);

        response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-success");
    }

    // Resend Token
    @RequestMapping(
            value    = "api/registration-token-resend",
            method   = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<VerificationToken> resendToken(@RequestBody String email) {

        // FIXME Protection
        User user = userService.findOne(email);
        VerificationToken token = verificationTokenService.getByUserId(user.getUserId());

        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + Constants.MailParameters.TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();

        token.setToken(tokenValue);
        token.setExpiryDate(date);

        VerificationToken tkn = verificationTokenService.update(token);
        // TODO Send Mail


        return new ResponseEntity<VerificationToken>(tkn, HttpStatus.OK);
    }

}
