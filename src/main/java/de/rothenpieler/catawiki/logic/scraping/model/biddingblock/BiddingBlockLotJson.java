package de.rothenpieler.catawiki.logic.scraping.model.biddingblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BiddingBlockLotJson {

    @JsonProperty
    private boolean is_sold;
    @JsonProperty
    private boolean reserve_price_met;
    @JsonProperty
    private boolean is_closed;
    @JsonProperty
    private boolean close_to_reserve_price;
    @JsonProperty
    private long highest_bid_amount;
    @JsonProperty
    private long next_minimum_bid;
    @JsonProperty
    private List<Long> quick_bids;


    /*
     "lot": {
      "planned_close_at": "2022-10-02T18:02:30Z",
      "planned_start_at": "2022-09-16T10:00:00Z",
      "highest_bid_amount": 70100,
      "highest_bidder_token": "3cef94d7563ace32132c111166fac6edaad3de7e",
      "id": 62152943,
      "is_closed": true,
      "is_sold": false,
      "next_minimum_bid": 71100,
      "quick_bids": [
        71100,
        72100,
        95000
      ],
      "reserve_price_met": false,
      "close_to_reserve_price": false,
      "show_flyin": false,
      "realtime_channel": "CWAUCTION-production-698973"
    },
     */


}
