package de.rothenpieler.catawiki.logic.scraping.model.allauctions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AllAuctionsJsonParent {

    @JsonProperty("auctions")
    private List<AuctionJson> auctions;

}
