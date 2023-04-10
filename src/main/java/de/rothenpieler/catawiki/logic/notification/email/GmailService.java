package de.rothenpieler.catawiki.logic.notification.email;

import de.rothenpieler.catawiki.exception.CanNotSendNotificationViaEmailException;
import de.rothenpieler.catawiki.model.notification.Notification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GmailService {

    /**
     * Sends an email using Gmail for the given notification
     *
     * @param notification notification to be sent
     * @throws CanNotSendNotificationViaEmailException in case of any error
     */
    public void sendNotificationViaEmail(@NonNull Notification notification) throws CanNotSendNotificationViaEmailException {
        log.debug("Sending notification for " + notification.getNotificationTitle());


    }

}
