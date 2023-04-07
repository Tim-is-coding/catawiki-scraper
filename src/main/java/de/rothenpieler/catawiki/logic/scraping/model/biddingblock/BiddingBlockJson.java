package de.rothenpieler.catawiki.logic.scraping.model.biddingblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Json representation for
 * https://www.catawiki.com/buyer/api/v3/lots/SOME_AUCTION_ITEM_ID/bidding_block?currency_code=EUR&locale=en
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BiddingBlockJson {

    @JsonProperty
    private String status;
    @JsonProperty
    private boolean is_a_bidder;
    @JsonProperty
    private List<BiddingBlockBidJson> bids;
    @JsonProperty
    private BiddingBlockLotJson lot;


}
