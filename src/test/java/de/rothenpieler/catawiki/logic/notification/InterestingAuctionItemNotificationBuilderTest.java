package de.rothenpieler.catawiki.logic.notification;

import de.rothenpieler.catawiki.logic.notification.searchrequest.SearchRequestService;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class InterestingAuctionItemNotificationBuilderTest {

    @Autowired
    private SearchRequestService notificationBuilder;

   // @Test
    void findMatchesForSearchRequests() {
        List<AuctionItem> matches = notificationBuilder.findMatchesForSearchRequests();
        Assertions.assertNotNull(matches);
    }
}