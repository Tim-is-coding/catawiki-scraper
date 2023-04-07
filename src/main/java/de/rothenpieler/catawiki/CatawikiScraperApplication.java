package de.rothenpieler.catawiki;

import de.rothenpieler.catawiki.exception.CanNotQueryAllCarsAtAuctionException;
import de.rothenpieler.catawiki.exception.CanNotQueryBidsException;
import de.rothenpieler.catawiki.logic.notification.InterestingAuctionItemNotificationBuilder;
import de.rothenpieler.catawiki.logic.scraping.LookForNewAuctionsScrapingAction;
import de.rothenpieler.catawiki.logic.scraping.QueryBidsOnAuctioningItemScrapingAction;
import de.rothenpieler.catawiki.logic.util.LoggingTool;
import de.rothenpieler.catawiki.model.catawiki.Auction;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.mongodb.AuctionItemRepository;
import de.rothenpieler.catawiki.mongodb.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains Main class and entry point for spring app
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Slf4j
@EnableMongoRepositories
@SpringBootApplication
public class CatawikiScraperApplication {


    @Autowired
    private InterestingAuctionItemNotificationBuilder notificationBuilder;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionItemRepository auctionItemRepository;


    public static void main(String[] args) {

        SpringApplication.run(CatawikiScraperApplication.class, args);
    }


    @Scheduled(fixedDelay = 1000 * 60)
    public void lookForInterestingCars() {

        List<AuctionItem> carsOfInterest = notificationBuilder.findMatchesForSearchRequests();
        log.info("(NOTIFICATION) Interesting cars=" + carsOfInterest);
    }

    /**
     * Scheduled method will scrape all data of oldtimer auctions.<br>
     *
     * @throws CanNotQueryAllCarsAtAuctionException if running auctions can not be loaded
     * @throws CanNotQueryBidsException             if the bids can not be queried for an auctioning item
     * @throws InterruptedException                 if scheduler is interrupted
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 3) // 3 hours
    public void collectData() throws CanNotQueryAllCarsAtAuctionException, CanNotQueryBidsException, InterruptedException {

        log.info("Updating data...");

        List<Auction> auctions =
                new LookForNewAuctionsScrapingAction().queryAllRunningAuctions();


        int counter = 0;
        AtomicInteger totalBids = new AtomicInteger();
        auctions.stream().forEach(e -> totalBids.addAndGet(e.getAuctionItems().size()));

        LoggingTool.logProgressInPercentage(counter, totalBids.get());
        for (Auction auction : auctions) {
            for (AuctionItem auctionItem : auction.getAuctionItems()) {

                new QueryBidsOnAuctioningItemScrapingAction(auctionItem).updateBids();
                auctionItemRepository.save(auctionItem);

                TimeUnit.MILLISECONDS.sleep(100);
                counter++;
            }

            LoggingTool.logProgressInPercentage(counter, totalBids.get());

            // save data to MongoDB. UpdATES/INSERTS automatically
            auctionRepository.save(auction);
        }

        log.info("Update done!");

    }


}
