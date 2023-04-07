package de.rothenpieler.catawiki.logic.scraping;

import de.rothenpieler.catawiki.exception.CanNotQueryAllCarsAtAuctionException;
import de.rothenpieler.catawiki.model.catawiki.Auction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class LookForNewAuctionsScrapingActionTest {

    @Test
    void testScrapingAction() throws CanNotQueryAllCarsAtAuctionException {

        //when
        List<Auction> auctions =
                new LookForNewAuctionsScrapingAction().queryAllRunningAuctions();

        // assert
        Assertions.assertTrue(auctions.size() >= 2, "Can not find auctions!");
        log.info(auctions.get(0).toString());
    }

}