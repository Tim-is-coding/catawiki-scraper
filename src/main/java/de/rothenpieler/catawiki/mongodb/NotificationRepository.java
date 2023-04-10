package de.rothenpieler.catawiki.mongodb;

import de.rothenpieler.catawiki.model.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, Long> {
}
