package de.rothenpieler.catawiki.model.notification;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import lombok.Getter;

import java.util.List;

public abstract class AbstractNotification {

    @Getter
    List<AuctionItem> auctionItems;

    AbstractNotification(List<AuctionItem> auctionItems) {
        this.auctionItems = auctionItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractNotification that = (AbstractNotification) o;
        for (AuctionItem item : auctionItems) {
            boolean auctionItemWasIncludedInOtherNotification = false;
            for (AuctionItem auctionItem : that.getAuctionItems()) {
                if (auctionItem.getUrl().equalsIgnoreCase(item.getUrl())) {
                    auctionItemWasIncludedInOtherNotification = true;
                    break;
                }
            }
            if (!auctionItemWasIncludedInOtherNotification) {
                return false;
            }
        }
        return true;
    }


}
