package de.rothenpieler.catawiki.logic.scraping.model.allauctions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class LotsJsonParent {

    private List<AuctionItemJson> lots;

}
