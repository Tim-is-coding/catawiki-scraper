package de.rothenpieler.catawiki.logic.notification;

import de.rothenpieler.catawiki.model.notification.AbstractNotification;
import org.springframework.stereotype.Service;

@Service
public class NotificationBuilder {


    public boolean sameNotificationWasAlreadySend(AbstractNotification notification) {
        // TODO load notifications from DB and compare
        return false;
    }

}
