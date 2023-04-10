package de.rothenpieler.catawiki.logic.util;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.catawiki.Bid;
import lombok.NonNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Optional;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
public class AuctionItemUtil {


    /**
     * Determines if one or more of the current bids reached the reserve price
     *
     * @return
     */
    public static boolean isReservePriceReached(@NonNull final AuctionItem auctionItem) {
        // check if any reserve price is set
        if (!auctionItem.isHasReservePrice()) {
            return true;
        }

        return auctionItem.isReservePriceMet();
    }

    /**
     * Determines if the current highest bid has met the experts estimates minimum
     *
     * @param auctionItem
     * @return
     */
    public static boolean isExpertMinimumEstimateMet(@NonNull final AuctionItem auctionItem) {
        Optional<Bid> highestBid = getHighestBid(auctionItem);
        if (highestBid.isEmpty()) {
            return false; // no bids yet
        }

        Money amountOnHighestBid = highestBid.get().gettAmount();
        Money minimumEstimate = auctionItem.getExpertEstimate().getMinimumEstimate();

        return minimumEstimate.isLessThan(amountOnHighestBid);
    }

    /**
     * Determines if the current highest bid has met the experts estimates maximum
     *
     * @param auctionItem
     * @return
     */
    public static boolean isExpertMaximumEstimateMet(@NonNull final AuctionItem auctionItem) {
        Optional<Bid> highestBid = getHighestBid(auctionItem);
        if (highestBid.isEmpty()) {
            return false; // no bids yet
        }

        Money amountOnHighestBid = highestBid.get().gettAmount();
        Money mmaximumEstimatemEstimate = auctionItem.getExpertEstimate().getMaximumEstimate();

        return mmaximumEstimatemEstimate.isLessThan(amountOnHighestBid);
    }

    /**
     * Computest the reserve price, if possible
     *
     * @param auctionItem auction to be checked
     * @return reserve price, if one can be found
     */
    public static Optional<Money> getReservePrice(@NonNull final AuctionItem auctionItem) {
        try {
            return Optional.of(auctionItem.gettHighestQuickBidSuggestion());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Calculates the currently highest bid, if present
     *
     * @param auctionItem
     * @return
     */
    public static Optional<Bid> getHighestBid(@NonNull final AuctionItem auctionItem) {
        if (auctionItem.getBids() == null || auctionItem.getBids().isEmpty()) {
            return Optional.empty(); // no bids yet
        }

        Bid max = null;
        for (Bid bid : auctionItem.getBids()) {

            if (max == null) {
                max = bid;
            }

            if (max.gettAmount().isLessThan(bid.gettAmount())) {
                max = bid;
            }
        }

        return Optional.ofNullable(max);
    }

    /**
     * Returns the reserve price or the highest bid - whichever is higher.
     *
     * @param auctionItem
     * @return
     */
    public static Money getReservePriceOrHigherBidIfExisting(@NonNull final AuctionItem auctionItem) {

        if (auctionItem.isHasReservePrice()) {
            if (!isReservePriceReached(auctionItem)) {
                return getReservePrice(auctionItem).get();
            }
        }

        Optional<Bid> highestBid = getHighestBid(auctionItem);
        if (highestBid.isPresent()) {
            return highestBid.get().gettAmount();
        }

        // neither a bid nor a reserve rice is given
        return Money.of(CurrencyUnit.EUR, 0);
    }

}
