package com.bacovakuhinja.controller;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

        // Wrong token
        if (verificationToken == null) {
            //response.setHeader("Location", "http://localhost:8091/index.html#/registration-confirm-wrong-link");
            response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-wrong-link");
            return;
        }

        Date currentTime = new Date();

        if (currentTime.after(verificationToken.getExpiryDate())) {
            response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-expired-link");
            return;
        }

        User user = verificationToken.getUser();
        user.setVerified("verified");

        // Update user
        userService.update(user);
        // Delete token
        // verificationTokenService.delete(verificationToken);

        response.sendRedirect("http://localhost:8091/index.html#/registration-confirm-success");
    }

}
