package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents the auction of a car
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Data
public class AuctionItem {

    @Id
    @BsonProperty()
    private long id;
    @BsonProperty()
    private long auctionId;
    @BsonProperty()
    private String url;
    @BsonProperty()
    private String title;
    private String subTitle;
    @BsonProperty()
    private Date auctionStartingAt;
    @BsonProperty()
    private List<Bid> bids;
    @BsonProperty()
    private boolean hasReservePrice;
    private ExpertEstimate expertEstimate;
    private long highestQuickBidSuggestion; // highest quick bid is suspected to be
    @BsonProperty()
    private boolean reservePriceMet;

    public AuctionItem() {

    }

    public Money gettHighestQuickBidSuggestion() {
        return Money.of(CurrencyUnit.EUR, highestQuickBidSuggestion);
    }

    public void settHighestQuickBidSuggestion(Money highestQuickBidSuggestion) {
        this.highestQuickBidSuggestion = highestQuickBidSuggestion.getAmountMajorLong();
    }

    @Override
    public String toString() {
        return title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionItem that = (AuctionItem) o;
        return id == that.id && auctionId == that.auctionId && hasReservePrice == that.hasReservePrice && Objects.equals(url, that.url) && Objects.equals(title, that.title) && Objects.equals(subTitle, that.subTitle) && Objects.equals(auctionStartingAt, that.auctionStartingAt) && Objects.equals(expertEstimate, that.expertEstimate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, auctionId, url, title, subTitle, auctionStartingAt, hasReservePrice, expertEstimate);
    }
}
