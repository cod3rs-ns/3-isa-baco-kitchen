package com.bacovakuhinja.aspects;

import com.bacovakuhinja.model.*;
import com.bacovakuhinja.service.*;
import com.bacovakuhinja.utility.CharUtils;
import com.bacovakuhinja.utility.Constants;
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

        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + Constants.MailParameters.TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();

        VerificationToken token = new VerificationToken(tokenValue, date, userService.findOne(guest.getEmail()));
        verificationTokenService.create(token);

        // FIXME
        // Message body
        final String message = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\"  content=\"text/html charset=UTF-8\" />" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + guest.getFirstName() + "!</h1>\n" +
                "        <p>Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        // FIXME
        System.out.println(Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue);
        sendMail(guest.getEmail(), "Potvrda registracije za sajt Baćova kuhinja", message);
    }

    @After(value = "@annotation(com.bacovakuhinja.annotations.SendProvidersMail) && args(offerId, responseId,..)")
    public void sendProvidersRejectionMail(Integer offerId, Integer responseId) throws MessagingException {
        // Message body
        final String messageAccept = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\"  content=\"text/html charset=UTF-8\" />" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Vesti iz Ba&#263;ove kuhinje!</h1>\n" +
                "           <p>Va&#353;a ponuda za porud&#382;binu #" + offerId + " je <strong style=\"color:green;\">prihva&#263;ena</strong>." +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        final String messageReject = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\"  content=\"text/html charset=UTF-8\" />" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Vesti iz Ba&#263;ove kuhinje!</h1>\n" +
                "           <p>Va&#353;a ponuda za porud&#382;binu #" + offerId + " je <strong style=\"color:red;\">odbijena</strong>." +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        OfferRequest offer = offerRequestService.findOne(offerId);
        ProviderResponse singleResponse = providerResponseService.findOne(responseId);
        if (offer.getAcceptedResponse() == null) {
            Collection <ProviderResponse> responses = providerResponseService.findAllByOffer(offer);
            for (ProviderResponse response : responses) {
                if (response.getResponseId() == offer.getAcceptedResponse()) {
                    System.out.println(response.getProvider().getEmail());
                    sendMail(response.getProvider().getEmail(), "Prihvaćena ponuda", messageAccept);
                } else {
                    sendMail(response.getProvider().getEmail(), "Odbijena ponuda", messageReject);
                    System.out.println(response.getProvider().getEmail());
                }
            }
        } else {
            sendMail(singleResponse.getProvider().getEmail(), "Odbijena ponuda", messageReject);
        }
    }

    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(provider,..)")
    public void sendNewProviderMail(RestaurantProvider provider) throws MessagingException {
        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + Constants.MailParameters.TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();

        VerificationToken token = new VerificationToken(tokenValue, date, userService.findOne(provider.getEmail()));
        verificationTokenService.create(token);

        // Message body
        final String message = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\"  content=\"text/html; charset=UTF-8\" />" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " +
                CharUtils.getASCIIFromString(provider.getFirstName()) + "!</h1>\n" +
                "        <p>Ovo je automatski izgenerisana poruka sistema Ba&#263;ova kuhinja. <br/>" +
                "           Upravo ste pozvani da se pridru&#382;ite na&#353;oj aplikaciji kao ponu&#273;a&#269;. <br/><br/> " +
                "           Va&#353;a &#353;ifra je: <b> " + provider.getPassword() + " </b><br/><br/>" +
                "           Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        User user = userService.findOne(provider.getEmail());
        user.setPassword(PasswordHelper.getSha256(user.getPassword()));
        userService.update(user);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue);
        //sendMail(provider.getEmail(), "Potvrda registracije za sajt Baćova kuhinja", message);
    }


    @After(value = "@annotation(com.bacovakuhinja.annotations.SendEmail) && args(manager, restaurantId, ..)")
    public void sendNewRestaurantManagerMail(RestaurantManager manager, Integer restaurantId) throws MessagingException {
        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + Constants.MailParameters.TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();

        VerificationToken token = new VerificationToken(tokenValue, date, userService.findOne(manager.getEmail()));
        verificationTokenService.create(token);

        // Message body
        final String message = "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\"  content=\"text/html; charset=UTF-8\" />" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + CharUtils.getASCIIFromString(manager.getFirstName()) + "!</h1>\n" +
                "        <p>Ovo je automatski izgenerisana poruka sistema Ba&#263;ova kuhinja. <br/>" +
                "           Upravo ste pozvani da se pridru&#382;ite na&#353;oj aplikaciji kao menad&#382;er restorana <b>" + CharUtils.getASCIIFromString(restaurantService.findOne(restaurantId).getName()) +"</b>. <br/><br/> " +
                "           Va&#353;a &#353;ifra je: <b> " + manager.getPassword() + " </b><br/><br/>" +
                "           Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        User user = userService.findOne(manager.getEmail());
        user.setPassword(PasswordHelper.getSha256(user.getPassword()));
        userService.update(user);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue);
        //sendMail(manager.getEmail(), "Potvrda registracije za sajt Baćova kuhinja", message);
    }

    @Async
    public static void sendMail(String address, String subject, String message) throws MessagingException {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.put("mail.smtp.host", Constants.MailParameters.HOST_NAME);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", Constants.MailParameters.HOST_PORT);
        sender.setJavaMailProperties(properties);

        Session mailSession = Session.getDefaultInstance(properties, null);
        MimeMessage mailMessage = new MimeMessage(mailSession);

        final InternetAddress recipient = new InternetAddress(address);

        mailMessage.addRecipient(Message.RecipientType.TO, recipient);
        mailMessage.setSubject(subject);
        mailMessage.setContent(message, "text/html");

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(Constants.MailParameters.HOST_NAME, Constants.MailParameters.AUTH_USER, Constants.MailParameters.AUTH_PASS);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
    }
}
