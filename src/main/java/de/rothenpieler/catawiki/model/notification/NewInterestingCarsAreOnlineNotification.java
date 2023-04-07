package de.rothenpieler.catawiki.model.notification;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;

import java.util.List;

public class NewInterestingCarsAreOnlineNotification extends AbstractNotification {
    NewInterestingCarsAreOnlineNotification(List<AuctionItem> auctionItems) {
        super(auctionItems);
    }
}
