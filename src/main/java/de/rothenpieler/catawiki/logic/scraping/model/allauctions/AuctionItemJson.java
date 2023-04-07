package de.rothenpieler.catawiki.logic.scraping.model.allauctions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AuctionItemJson {

    @JsonProperty
    private long id;
    @JsonProperty
    private String title;
    @JsonProperty
    private String subtitle;
    @JsonProperty
    private String url;
    @JsonProperty
    private long auctionId;
    @JsonProperty
    private boolean reservePriceSet;
    @JsonProperty
    private Date biddingStartTime;

}
