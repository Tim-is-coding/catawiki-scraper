package de.rothenpieler.catawiki.logic.scraping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rothenpieler.catawiki.exception.CanNotQueryBidsException;
import de.rothenpieler.catawiki.logic.scraping.model.biddingblock.BiddingBlockBidJson;
import de.rothenpieler.catawiki.logic.scraping.model.biddingblock.BiddingBlockJsonParent;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.catawiki.Bid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Slf4j
public class QueryBidsOnAuctioningItemScrapingAction {

    private final AuctionItem auctionItem;

    public QueryBidsOnAuctioningItemScrapingAction(@NonNull AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
    }


    /**
     * Queries all bids on the {@link AuctionItem} - no matter if the auction is closed or still running
     *
     * @throws CanNotQueryBidsException in case of any exception
     */
    public void updateBids() throws CanNotQueryBidsException {
        try {
            log.debug("Now querying bids for auction item: " + auctionItem.getTitle());

            String url = "https://www.catawiki.com/buyer/api/v3/lots/" + auctionItem.getId() + "/bidding_block?currency_code=EUR";

            String json = IOUtils.toString(URI.create(url), StandardCharsets.UTF_8);

            BiddingBlockJsonParent biddingBlockJson = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(json, BiddingBlockJsonParent.class);

            // update quick-bid value
            List<Long> quick_bids = biddingBlockJson.getBidding_block().getLot().getQuick_bids();
            long maxQuickBidOption = quick_bids.stream().mapToLong(Long::longValue).max().getAsLong();
            auctionItem.settHighestQuickBidSuggestion(Money.of(CurrencyUnit.EUR, maxQuickBidOption));

            // check if reserve price is met
            boolean reservePriceMet = biddingBlockJson.getBidding_block().getLot().isReserve_price_met();
            auctionItem.setReservePriceMet(reservePriceMet);

            // parse all bids
            List<Bid> bids = new ArrayList<>();
            List<BiddingBlockBidJson> bidsJson = biddingBlockJson.getBidding_block().getBids();
            for (BiddingBlockBidJson bidJson : bidsJson) {
                Bid bid = new Bid();
                bid.setBidPlacedAt(bidJson.getCreated_at());
                bid.settAmount(Money.of(CurrencyUnit.EUR, bidJson.getAmount()));
                bid.setBidderName(bidJson.getBidder().getName());
                bids.add(bid);
            }
            auctionItem.setBids(bids);

        } catch (Exception e) {
            String errorMessage = "Unexpected error while parsing bids";
            log.error(errorMessage, e);
            throw new CanNotQueryBidsException(errorMessage, e);
        }
    }
}