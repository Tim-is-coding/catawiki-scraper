package de.rothenpieler.catawiki.logic.notification.email;

import de.rothenpieler.catawiki.exception.CanNotSendNotificationViaEmailException;
import de.rothenpieler.catawiki.model.notification.Notification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
@Slf4j
public class EmailService {

    @Value("${email.username}")
    private String emailUsername;

    @Value("${email.password}")
    private String emailPassword;

    /**
     * Sends an email using gmx/SMTP for the given notification
     *
     * @param notification notification to be sent
     * @throws CanNotSendNotificationViaEmailException in case of any error
     */
    public void sendNotificationViaEmail(@NonNull Notification notification) throws CanNotSendNotificationViaEmailException {
        try {
            log.debug("Sending notification " + notification);
            log.info(emailUsername);
            sendNotificationViaGmx(notification);
            log.info("Successfully send gmx notification");
        } catch (
                Exception e) {
            log.error("Error while sending email via gmx", e);
            throw new CanNotSendNotificationViaEmailException(e);
        }

    }

    private void sendNotificationViaGmx(Notification notification) throws MessagingException {

        // Recipient's email ID needs to be mentioned.
        String to = "tim.rothenpieler1999@gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "mail.gmx.net");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user", emailUsername);
        properties.put("mail.smtp.password", emailPassword);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "false");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(emailUsername));

        // Set To: header field of the header.
        message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject(notification.getNotificationTitle());

        // Now set the actual message
        message.setText(notification.buildNotificationText());

        // Send message
        Transport.send(message);
    }

}
