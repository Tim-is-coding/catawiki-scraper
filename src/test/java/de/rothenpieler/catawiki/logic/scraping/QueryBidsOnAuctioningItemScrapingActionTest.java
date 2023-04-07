package de.rothenpieler.catawiki.logic.scraping;

import de.rothenpieler.catawiki.exception.CanNotQueryAllCarsAtAuctionException;
import de.rothenpieler.catawiki.exception.CanNotQueryBidsException;
import de.rothenpieler.catawiki.model.catawiki.Auction;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class QueryBidsOnAuctioningItemScrapingActionTest {


    @Test
    void testScrapingAction() throws CanNotQueryAllCarsAtAuctionException, CanNotQueryBidsException {

        //when
        List<Auction> auctions =
                new LookForNewAuctionsScrapingAction().queryAllRunningAuctions();
        Assertions.assertTrue(auctions.size() >= 1, "Can not find auctions!");

        List<AuctionItem> auctionItems = auctions.get(0).getAuctionItems();
        Assertions.assertTrue(auctionItems.size() >= 1, "Can not find auction items!");

        AuctionItem auctionItem = auctionItems.get(0);
        new QueryBidsOnAuctioningItemScrapingAction(auctionItem).updateBids();
        log.info(auctionItem.toString());
        Assertions.assertNotNull(auctionItem.getBids(), "Bids are null!");
    }

}