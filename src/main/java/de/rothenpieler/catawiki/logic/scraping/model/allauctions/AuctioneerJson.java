package de.rothenpieler.catawiki.logic.scraping.model.allauctions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AuctioneerJson {

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

}
