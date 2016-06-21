package com.bacovakuhinja.utility;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public final class MailGenerator{

    public static final String HEADER  = "<head>\n" +
                                         "<meta http-equiv=\"Content-Type\"  content=\"text/html; charset=UTF-8\" />" +
                                         "</head>\n";

    public static final String LOGO = "<div style=\"width: 15%; float: left;\">\n" +
                                      "<img src=\"http://s22.postimg.org/o2lyr9qmp/Untitled_2_transparent.png\" width=\"100%\"/>\n" +
                                      "</div>\n" +
                                      "\n";

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

    public class RegisterProviderMail {

        private String firstName;
        private String password;
        private String tokenValue;

        private String getBody(){
            String BODY = "<body>\n" + "<div>\n" +
                    LOGO +
                    "    <div style=\"width: 85%; float: left;\">\n" +
                    "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + firstName + "!</h1>\n" +
                    "        <p>Ovo je automatski izgenerisana poruka sistema Ba&#263;ova kuhinja. <br/>" +
                    "           Upravo ste pozvani da se pridru&#382;ite na&#353;oj aplikaciji kao ponu&#273;a&#269;. <br/><br/> " +
                    "           Va&#353;a &#353;ifra je: <b> " + password + " </b><br/><br/>" +
                    "           Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n";

            return BODY;
        }

        public RegisterProviderMail(String address, String subject, String fn, String pass, String token){
            this.firstName = CharUtils.getASCIIFromString(fn);
            this.password = pass;
            this.tokenValue = token;

            String message = "<html>\n" + HEADER + getBody() + "</html>\n";
            try {
                sendMail(address, subject, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public class RegisterRManagerMail{

        private String firstName;
        private String restaurantName;
        private String password;
        private String tokenValue;

        public RegisterRManagerMail(String address, String subject, String fn, String pass, String restaurantName, String token){
            this.firstName = CharUtils.getASCIIFromString(fn);
            this.restaurantName = CharUtils.getASCIIFromString(restaurantName);
            this.password = pass;
            this.tokenValue = token;

            String message = "<html>\n" + HEADER + getBody() + "</html>\n";
            try {
                sendMail(address, subject, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        private String getBody(){
            String body =
                    "<body>\n" +
                    "<div>\n" + LOGO +
                    "    <div style=\"width: 85%; float: left;\">\n" +
                    "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + firstName + "!</h1>\n" +
                    "        <p>Ovo je automatski izgenerisana poruka sistema Ba&#263;ova kuhinja. <br/>" +
                    "           Upravo ste pozvani da se pridru&#382;ite na&#353;oj aplikaciji kao menad&#382;er restorana <b>" + restaurantName +"</b>. <br/><br/> " +
                    "           Va&#353;a &#353;ifra je: <b> " + password + " </b><br/><br/>" +
                    "           Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n";
            return body;
        }
    }

    public class RegisterGuestMail{
        private String firstName;
        private String tokenValue;

        public RegisterGuestMail(String address, String subject, String fn, String token){
            this.firstName = CharUtils.getASCIIFromString(fn);
            this.tokenValue = token;

            String message = "<html>\n" + HEADER + getBody() + "</html>\n";
            try {
                sendMail(address, subject, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        private String getBody(){
            String body = "<body>\n" +
                    "<div>\n" + LOGO +
                    "    <div style=\"width: 85%; float: left;\">\n" +
                    "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + firstName + "!</h1>\n" +
                    "        <p>Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n";
            return body;
        }
    }

    public class ProviderOfferMail{
        private int offerId;
        private String accepted;

        public ProviderOfferMail(int offerId, boolean accept, String email){
            this.offerId = offerId;
            String subject;

            if(accept) {
                accepted = "prihva&#263;ena";
                subject = "Prihvaćena ponuda";
            }
            else{
                accepted = "odbijena";
                subject = "Odbijena ponuda";
            }

            try {
                sendMail(email, subject, getMessage());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        public String getMessage(){
            String message =
                    "<html>\n" + HEADER +
                    "<body>\n" +
                    "<div>\n" + LOGO +
                    "    <div style=\"width: 85%; float: left;\">\n" +
                    "        <h1>Vesti iz Ba&#263;ove kuhinje!</h1>\n" +
                    "           <p>Va&#353;a ponuda za porud&#382;binu #" + offerId + " je <strong style=\"color:green;\">"+ accepted + "</strong>." +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            return message;
        }
    }

    public class RegisterEmployeeMail{

        private String firstName;
        private String restaurantName;
        private String password;
        private String tokenValue;

        public RegisterEmployeeMail(String address, String subject, String fn, String pass, String restaurantName, String role, String token){
            this.firstName = CharUtils.getASCIIFromString(fn);
            this.restaurantName = CharUtils.getASCIIFromString(restaurantName);
            this.password = pass;
            this.tokenValue = token;

            String messageRole;
            if(role.equals(Constants.UserRoles.COOK))
                messageRole = "kuvar";
            else if(role.equals(Constants.UserRoles.BARTENDER))
                messageRole = "&#353;anker";
            else
                messageRole = "konobar";

            String message = "<html>\n" + HEADER + getBody(messageRole) + "</html>\n";
            try {
                sendMail(address, subject, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        private String getBody(String role){
            String body =
                    "<body>\n" +
                            "<div>\n" + LOGO +
                            "    <div style=\"width: 85%; float: left;\">\n" +
                            "        <h1>Dobro do&#353;li u Ba&#263;ovu kuhinju, " + firstName + "!</h1>\n" +
                            "        <p>Ovo je automatski izgenerisana poruka sistema Ba&#263;ova kuhinja. <br/>" +
                            "           Upravo ste pozvani da se pridru&#382;ite na&#353;oj aplikaciji kao " + role + " restorana <b>" + restaurantName +"</b>. <br/><br/> " +
                            "           Va&#353;a &#353;ifra je: <b> " + password + " </b><br/><br/>" +
                            "           Da biste potvrdili registraciju na sajtu, potrebno je da kliknete <a href=\"" + Constants.MailParameters.TOKEN_CONFIRM_LINK + tokenValue + "\">ovdje</a>.</p>\n" +
                            "    </div>\n" +
                            "</div>\n" +
                            "</body>\n";
            return body;
        }
    }

}