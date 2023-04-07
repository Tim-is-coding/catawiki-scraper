package de.rothenpieler.catawiki.model.notification;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;

import java.util.List;

public class TimeIsRunningUpNotification extends AbstractNotification {
    TimeIsRunningUpNotification(List<AuctionItem> auctionItems) {
        super(auctionItems);
    }
}
