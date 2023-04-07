package de.rothenpieler.catawiki.logic.scraping.model.biddingblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BiddingBlockBidJson {

    @JsonProperty
    private String id;
    @JsonProperty
    private long amount;
    @JsonProperty
    private Date created_at;
    @JsonProperty
    private boolean from_order;
    @JsonProperty
    private BiddingBlockBidBidderJson bidder;

    /*
      "bids": [
      {
        "id": 208243479,
        "bidder": {
          "name": "Bidder 3240",
          "token": "3cef94d7563ace32132c111166fac6edaad3de7e"
        },
        "amount": 70100,
        "created_at": "2022-10-02T16:15:41Z",
        "from_order": false
      },
      {
        "id": 208242653,
        "bidder": {
          "name": "Bidder 9153",
          "token": "05cb70a9adfdf736147ad6b4d0aa4d2e1f5d86aa"
        },
        "amount": 69100,
        "created_at": "2022-10-02T16:13:05Z",
        "from_order": false
      },
     */

}
