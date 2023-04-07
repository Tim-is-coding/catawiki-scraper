package de.rothenpieler.catawiki.logic.notification;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class InterestingAuctionItemNotificationBuilderTest {

    @Autowired
    private InterestingAuctionItemNotificationBuilder notificationBuilder;

    @Test
    void findMatchesForSearchRequests() {
        List<AuctionItem> matches = notificationBuilder.findMatchesForSearchRequests();
        Assertions.assertNotNull(matches);
    }
}