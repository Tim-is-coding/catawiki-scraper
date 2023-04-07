package de.rothenpieler.catawiki.logic.scraping.model.allauctions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AuctionJson {

    @JsonProperty
    private long id;

    @JsonProperty("close_at")
    private Date closeAt;


    @JsonProperty("start_at")
    private Date startAt;


    @JsonProperty
    private String title;

    @JsonProperty
    private String url;

    @JsonProperty
    private List<AuctioneerJson> auctioneers;

}
