package com.bacovakuhinja.aspects;

import com.bacovakuhinja.model.User;
import com.bacovakuhinja.model.VerificationToken;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.service.VerificationTokenService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    // 30 minutes
    private static final long   TOKEN_EXPIRE_TIME  = 1800000;
    private static final String TOKEN_CONFIRM_LINK = "http://localhost:8091/api/registrationConfirm?token=";

    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int    HOST_PORT = 587;
    private static final String AUTH_USER = "dmarjanovic94@gmail.com";
    private static final String AUTH_PASS = "kyajhnarjqmrwqif";

    @After(value = "@annotation(com.bacovakuhinja.annotations.Registration) && args(user)")
    public void sendConfirmationMail(User user) throws MessagingException {

        // Generate VerificationToken
        Date date = new Date();
        date.setTime(date.getTime() + TOKEN_EXPIRE_TIME);
        final String tokenValue = UUID.randomUUID().toString();

        System.out.println(date);
        System.out.println(user.getEmail());
        System.out.println(userService.findOne(user.getEmail()));

        VerificationToken token = new VerificationToken(tokenValue, date, userService.findOne(user.getEmail()));
        verificationTokenService.create(token);

        // Message body
        final String message = "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "    <div style=\"width: 15%; float: left;\">\n" +
                "        <img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                "    </div>\n" +
                "    \n" +
                "    <div style=\"width: 85%; float: left;\">\n" +
                "        <h1>Dobro došli, " + user.getFirstName() + "!</h1>\n" +
                "        <p>Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        System.out.println(TOKEN_CONFIRM_LINK + tokenValue);
        // sendMail(user.getEmail(), "Potvrda registracije za sajt Baćova kuhinja", message);
    }

    private void sendMail(String address, String subject, String message) throws MessagingException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST_NAME);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.port", HOST_PORT);
        sender.setJavaMailProperties(properties);

        Session mailSession = Session.getDefaultInstance(properties, null);
        MimeMessage mailMessage = new MimeMessage(mailSession);

        final InternetAddress recipient = new InternetAddress(address);

        mailMessage.addRecipient(Message.RecipientType.TO, recipient);
        mailMessage.setSubject(subject);
        mailMessage.setContent(message, "text/html");

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(HOST_NAME, AUTH_USER, AUTH_PASS);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
    }
}
