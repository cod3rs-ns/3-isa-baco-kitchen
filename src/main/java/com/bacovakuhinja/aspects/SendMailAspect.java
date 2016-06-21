package com.bacovakuhinja.aspects;

import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import com.bacovakuhinja.utility.CharUtils;
import com.bacovakuhinja.utility.Constants;
import com.bacovakuhinja.utility.MailGenerator;
import com.bacovakuhinja.utility.PasswordHelper;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Component
@Aspect
public class SendMailAspect {

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    UserService userService;

    @Autowired
    ProviderResponseService providerResponseService;

    @Autowired
    OfferRequestService offerRequestService;

    @Autowired
    RestaurantService restaurantService;

    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(guest,..)")
    public void sendConfirmationMail(Guest guest) throws MessagingException {
        String tokenValue = generateToken(guest.getEmail());

        new MailGenerator().new RegisterGuestMail(guest.getEmail(),"Potvrda registracije za sajt Baćova kuhinja", guest.getFirstName(), tokenValue);
    }

    @After(value = "@annotation(com.bacovakuhinja.annotations.SendProvidersMail) && args(offerId, responseId,..)")
    public void sendProvidersRejectionMail(Integer offerId, Integer responseId) throws MessagingException {
        OfferRequest offer = offerRequestService.findOne(offerId);
        ProviderResponse singleResponse = providerResponseService.findOne(responseId);
        if (offer.getAcceptedResponse() == null) {
            Collection <ProviderResponse> responses = providerResponseService.findAllByOffer(offer);
            for (ProviderResponse response : responses) {
                if (response.getResponseId() == offer.getAcceptedResponse()) {
                    System.out.println(response.getProvider().getEmail());
                    new MailGenerator().new ProviderOfferMail(offerId, true, response.getProvider().getEmail());
                } else {
                    System.out.println(response.getProvider().getEmail());
                    new MailGenerator().new ProviderOfferMail(offerId, false, response.getProvider().getEmail());
                }
            }
        } else {
            new MailGenerator().new ProviderOfferMail(offerId, false, singleResponse.getProvider().getEmail());
        }
    }


    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(provider,..)")
    public void sendNewProviderMail(RestaurantProvider provider) throws MessagingException {
        String tokenValue = generateToken(provider.getEmail());

        User user = userService.findOne(provider.getEmail());
        new MailGenerator().new RegisterProviderMail(user.getEmail(),"Potvrda registracije za sajt Baćova kuhinja", user.getFirstName(), user.getPassword(), tokenValue);

        //hashing password
        System.out.println(user.getPassword());
        user.setPassword(PasswordHelper.getSha256(user.getPassword()));
        userService.update(user);
    }


    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(manager, restaurantId, ..)")
    public void sendNewRestaurantManagerMail(RestaurantManager manager, Integer restaurantId) throws MessagingException {
        String tokenValue = generateToken(manager.getEmail());

        User user = userService.findOne(manager.getEmail());
        String restaurantName = restaurantService.findOne(restaurantId).getName();
        new MailGenerator().new RegisterRManagerMail(user.getEmail(),"Potvrda registracije za sajt Baćova kuhinja", user.getFirstName(), user.getPassword(), restaurantName, tokenValue);

        //hashing password
        System.out.println(user.getPassword());
        user.setPassword(PasswordHelper.getSha256(user.getPassword()));
        userService.update(user);
    }

    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(emp, ..)")
    public void sendNewEmployeeMail(Employee emp) throws MessagingException {
        String tokenValue = generateToken(emp.getEmail());

        User user = userService.findOne(emp.getEmail());
        String restaurantName = restaurantService.findOne(emp.getRestaurantID()).getName();
        new MailGenerator().new RegisterEmployeeMail(user.getEmail(),"Potvrda registracije za sajt Baćova kuhinja", user.getFirstName(), user.getPassword(), restaurantName, emp.getType(), tokenValue);

        //hashing password
        System.out.println(user.getPassword());
        user.setPassword(PasswordHelper.getSha256(user.getPassword()));
        userService.update(user);
    }

    private String generateToken(String userMail){
        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + Constants.MailParameters.TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();
        System.out.println(Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue);

        VerificationToken token = new VerificationToken(tokenValue, date, userService.findOne(userMail));
        verificationTokenService.create(token);

        return tokenValue;
    }
}
