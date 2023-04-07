package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * Represents an auction holding multiple {@link AuctionItem}s
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
@Data
public class Auction {

    @Id
    private long id;
    private String title;
    private Date startAt;
    private Date endAt;
    private String url;
    private List<AuctionItem> auctionItems;
    private List<Expert> auctioneers;

}
