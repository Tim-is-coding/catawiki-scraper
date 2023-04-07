package de.rothenpieler.catawiki.logic.notification;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.notification.NewInterestingCarsAreOnlineNotification;
import de.rothenpieler.catawiki.model.notification.TimeIsRunningUpNotification;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class NotificationSenderService {

    @Autowired
    private InterestingAuctionItemNotificationBuilder interestingAuctionItemNotificationBuilder;

    public void sendNotificationsIfRequired() {

        // load all matches for search requests
        List<AuctionItem> matchesForSearchRequests = interestingAuctionItemNotificationBuilder.findMatchesForSearchRequests();

        // build new cars online notification if necessary
        Optional<NewInterestingCarsAreOnlineNotification> newInterestingCarsAreOnlineNotification = buildNewObjectsArrivedNotificationIfNecessary(matchesForSearchRequests);

        // build time is running up notification if necessary
        Optional<TimeIsRunningUpNotification> timeIsRunningUpNotification = buildTimeIsRunningUpNotificationIfNecessary(matchesForSearchRequests);


    }


    private Optional<TimeIsRunningUpNotification> buildTimeIsRunningUpNotificationIfNecessary(@NonNull List<AuctionItem> matches) {

        return Optional.empty();
    }

    private Optional<NewInterestingCarsAreOnlineNotification> buildNewObjectsArrivedNotificationIfNecessary(@NonNull List<AuctionItem> matches) {

        // look for matches for search requests
        List<AuctionItem> matchesForSearchRequests = interestingAuctionItemNotificationBuilder.findMatchesForSearchRequests();

        // filter old matches that were already sent
        filterOutMatchesThatWereAlreadySent(matchesForSearchRequests);

        // send notification
        sendNotificationForNewMatches(matchesForSearchRequests);

        return Optional.empty();
    }

    private void sendNotificationForNewMatches(List<AuctionItem> matchesForSearchRequests) {

    }

    private void filterOutMatchesThatWereAlreadySent(List<AuctionItem> matchesForSearchRequests) {
    }

}
