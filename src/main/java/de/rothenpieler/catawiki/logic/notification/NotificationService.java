package de.rothenpieler.catawiki.logic.notification;

import de.rothenpieler.catawiki.exception.CanNotSendNotificationViaEmailException;
import de.rothenpieler.catawiki.logic.notification.email.GmailService;
import de.rothenpieler.catawiki.model.catawiki.Auction;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.notification.Notification;
import de.rothenpieler.catawiki.model.notification.NotificationType;
import de.rothenpieler.catawiki.mongodb.AuctionRepository;
import de.rothenpieler.catawiki.mongodb.NotificationRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private GmailService gmailService;

    @Value("${gmail.apikey}")
    private String gmailApiKey;

    @Autowired
    private InterestingAuctionItemNotificationBuilder interestingAuctionItemNotificationBuilder;

    private List<Notification> alreadySentNotifications;

    public void sendNotificationsIfRequired() {

        log.debug("Scanning database to look for potential new notifications");

        // pull latest data about already sent notifications from database to avoid sending same notifications again
        updateNotificationCache();

        // load all matches for search requests
        List<AuctionItem> matchesForSearchRequests = interestingAuctionItemNotificationBuilder.findMatchesForSearchRequests();

        log.debug("Found {} matches for the current search requests", matchesForSearchRequests.size());

        // build new cars online notification if necessary
        Optional<Notification> newInterestingCarsAreOnlineNotification = buildNewObjectsArrivedNotificationIfNecessary(matchesForSearchRequests);

        // build time is running up notification if necessary
        Optional<Notification> timeIsRunningUpNotification = buildTimeIsRunningUpNotificationIfNecessary(matchesForSearchRequests);

        if (newInterestingCarsAreOnlineNotification.isPresent()) {
            sendNotificationViaEmail(newInterestingCarsAreOnlineNotification.get());
        }

        if (timeIsRunningUpNotification.isPresent()) {
            sendNotificationViaEmail(timeIsRunningUpNotification.get());
        }
    }

    /**
     * Sends the notification via email and saves the notification to MongoDB afterward
     *
     * @param notification notification to be sent
     */
    private void sendNotificationViaEmail(@NonNull Notification notification) {

        log.info("Sending notification " + notification);

        try {
            // send email
            gmailService.sendNotificationViaEmail(notification);
        } catch (CanNotSendNotificationViaEmailException e) {
            log.error("Can not send email notification.", e);
            return;
        }

        // persist notification
        notification.setSentAt(new Date());
        notificationRepository.save(notification);
    }

    /**
     * Pulls all notifications that were already sent from the database and saves it to this objects field. This avoids
     * too many unnecessary database requests.
     */
    private void updateNotificationCache() {
        alreadySentNotifications = notificationRepository.findAll();
    }


    /**
     * Builds a notification for all car that will be sold within the next 3 hours.
     *
     * @param matches
     * @return
     */
    private Optional<Notification> buildTimeIsRunningUpNotificationIfNecessary(@NonNull List<AuctionItem> matches) {

        List<AuctionItem> noNotificationSendYet = filterOutMatchesThatWereAlreadySent(matches, NotificationType.AUCTION_WILL_END_SOON_NOTIFICATION);

        // check if any match will be sold within next three hours
        List<AuctionItem> willBeSoldSoon = findCarsThatWillBeSoldWithinNextThreeHours(noNotificationSendYet);

        if (willBeSoldSoon.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Notification(willBeSoldSoon, NotificationType.AUCTION_WILL_END_SOON_NOTIFICATION));
    }

    /**
     * Filters out all cars that are not sold within next 3 hours. <br>
     * Method accesses the database and should therefore not be called too frequently.
     *
     * @param matches
     * @return cars that will be sold within next 3 hours
     */
    private List<AuctionItem> findCarsThatWillBeSoldWithinNextThreeHours(List<AuctionItem> matches) {
        final Date dateInThreeHours = new Date(Instant.now().plus(3, ChronoUnit.HOURS).toEpochMilli());
        return matches.stream().filter(e -> getAuctionForAuctionItem(e).getEndAt().toInstant().isAfter(dateInThreeHours.toInstant())).toList();
    }

    /**
     * Loads the auction for the given auction item from the MongoDB.
     *
     * @param auctionItem
     * @return
     */
    private Auction getAuctionForAuctionItem(@NonNull AuctionItem auctionItem) {
        return auctionRepository.findById(auctionItem.getAuctionId()).orElseThrow(() -> new IllegalStateException("Can Not find auction for auction item " + auctionItem));
    }

    /**
     * Builds a notification that contains a list of all
     *
     * @param matches
     * @return
     */
    private Optional<Notification> buildNewObjectsArrivedNotificationIfNecessary(@NonNull List<AuctionItem> matches) {

        // filter old matches that were already sent
        List<AuctionItem> carsThatAreNewOnTheMarket = filterOutMatchesThatWereAlreadySent(matches, NotificationType.NEW_OLDTIMERS_ONLINE_NOTIFICATION);

        if (carsThatAreNewOnTheMarket.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Notification(carsThatAreNewOnTheMarket, NotificationType.NEW_OLDTIMERS_ONLINE_NOTIFICATION));
    }

    /**
     * Filters out all matches that were already included in a notification
     *
     * @param matchesForSearchRequests
     * @return
     */
    private List<AuctionItem> filterOutMatchesThatWereAlreadySent(List<AuctionItem> matchesForSearchRequests, NotificationType notificationType) {
        List<AuctionItem> notIncludedInAnyNotification = new ArrayList<>();

        for (AuctionItem match : matchesForSearchRequests) {
            long amountOfNotificationsThatIncludeMatch = alreadySentNotifications.stream().
                    filter(e -> e.getNotificationType() == notificationType).
                    filter(e -> e.getAuctionItems().contains(match)).count();

            if (amountOfNotificationsThatIncludeMatch == 0) {
                notIncludedInAnyNotification.add(match);
            }
        }

        return notIncludedInAnyNotification;
    }

}
