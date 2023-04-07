package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

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
}
