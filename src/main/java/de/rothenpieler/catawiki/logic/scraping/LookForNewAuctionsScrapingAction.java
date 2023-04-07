package de.rothenpieler.catawiki.logic.scraping;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rothenpieler.catawiki.exception.CanNotQueryAllCarsAtAuctionException;
import de.rothenpieler.catawiki.logic.scraping.model.allauctions.*;
import de.rothenpieler.catawiki.model.catawiki.Auction;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.catawiki.Expert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Looks up all running auctions.<br>
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Slf4j
public class LookForNewAuctionsScrapingAction {


    /**
     * Looks up all running auctions on catawiki.com and parses them
     *
     * @return all running auctions
     * @throws CanNotQueryAllCarsAtAuctionException in case of any error
     */
    public List<Auction> queryAllRunningAuctions() throws CanNotQueryAllCarsAtAuctionException {
        try {
            log.info("Starting to query for all oldtimers currently listed for auction");


            // load all lots
            String lotsUrl = "https://www.catawiki.com/en/buyer/api/v1/categories/423/lots?per_page=100&page=";
            List<AuctionItem> auctionItems = new ArrayList<>();
            for (int pageIndex = 1; pageIndex < 10; pageIndex++) {
                String lotsJson = IOUtils.toString(URI.create(lotsUrl + pageIndex), StandardCharsets.UTF_8);
                LotsJsonParent lotsJsonParent = new ObjectMapper().readValue(lotsJson, LotsJsonParent.class);

                for (AuctionItemJson lot : lotsJsonParent.getLots()) {
                    AuctionItem auctionItem = new AuctionItem();
                    auctionItem.setId(lot.getId());
                    auctionItem.setAuctionId(lot.getAuctionId());
                    auctionItem.setUrl(lot.getUrl());
                    auctionItem.setTitle(lot.getTitle());
                    auctionItem.setAuctionStartingAt(lot.getBiddingStartTime());
                    auctionItem.setHasReservePrice(lot.isReservePriceSet());

                    auctionItems.add(auctionItem);
                }

            }

            // now load all auctions
            String auctionsUrl = "https://www.catawiki.com/buyer/api/v1/categories/423/auctions?locale=en&page=1&status=open&per_page=100";
            String auctionsJson = IOUtils.toString(URI.create(auctionsUrl), StandardCharsets.UTF_8);
            AllAuctionsJsonParent auctionsJsonParent = new ObjectMapper().readValue(auctionsJson, AllAuctionsJsonParent.class);

            List<Auction> allAuctions = new ArrayList<>();
            for (AuctionJson auctionJson : auctionsJsonParent.getAuctions()) {
                Auction auction = new Auction();
                auction.setTitle(auctionJson.getTitle());
                auction.setUrl(auctionJson.getUrl());
                auction.setEndAt(auctionJson.getCloseAt());
                auction.setStartAt(auctionJson.getStartAt());
                auction.setId(auctionJson.getId());

                auction.setAuctioneers(new ArrayList<>());
                for (AuctioneerJson auctioneerJson : auctionJson.getAuctioneers()) {
                    Expert expert = new Expert();
                    expert.setName(auctioneerJson.getName());
                    expert.setId(auctioneerJson.getId());

                    auction.getAuctioneers().add(expert);
                }


                // link auction to auctioning items
                long auctionId = auction.getId();
                List<AuctionItem> auctionItemsForThisAuction = auctionItems.stream().filter(e -> e.getAuctionId() == auctionId).toList();
                auction.setAuctionItems(auctionItemsForThisAuction);

                allAuctions.add(auction);
            }

            return allAuctions;

        } catch (Exception e) {
            String errorMessage = "Unexpected error while querying/parsing new oldtimers";
            log.error(errorMessage, e);
            throw new CanNotQueryAllCarsAtAuctionException(errorMessage, e);
        }
    }

}
