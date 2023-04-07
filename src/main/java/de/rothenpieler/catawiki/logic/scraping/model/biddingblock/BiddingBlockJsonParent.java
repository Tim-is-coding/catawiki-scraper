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
public class BiddingBlockJsonParent {

    @JsonProperty("bidding_block")
    private BiddingBlockJson bidding_block;

}
