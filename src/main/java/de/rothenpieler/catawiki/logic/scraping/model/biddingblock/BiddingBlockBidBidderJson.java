package de.rothenpieler.catawiki.logic.scraping.model.biddingblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BiddingBlockBidBidderJson {
    @JsonProperty
    private String name;
    @JsonProperty
    private String token;

    /*
      "bidder": {
          "name": "Bidder 9153",
          "token": "05cb70a9adfdf736147ad6b4d0aa4d2e1f5d86aa"
        },
     */
}
