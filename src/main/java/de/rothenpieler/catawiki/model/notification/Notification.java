package de.rothenpieler.catawiki.model.notification;

import de.rothenpieler.catawiki.logic.util.AuctionItemUtil;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Date;
import java.util.List;

public class Notification {

    @Getter
    List<AuctionItem> auctionItems;

    @Getter
    private final NotificationType notificationType;

    @Getter
    @Setter
    private Date sentAt;

    public Notification(@NonNull List<AuctionItem> auctionItems, @NonNull NotificationType notificationType) {
        this.auctionItems = auctionItems;
        this.notificationType = notificationType;
    }


    /**
     * Build the human-readable text for the notification. <br>This text can be sent via email for example.
     *
     * @return notification text for the cars
     */
    public String buildNotificationText() {

        StringBuilder sb = new StringBuilder();

        switch (notificationType) {
            case NEW_OLDTIMERS_ONLINE_NOTIFICATION -> {
                sb.append("Es werden " + auctionItems.size() + " Oldtimer zu einem potentiell gutem Mindestpreis versteigert.\n\n");

                int counter = 1;
                for (AuctionItem auctionItem : auctionItems) {
                    sb.append(" [Inserat Nr. " + counter++ + "] ");
                    if (auctionItem.isHasReservePrice()) {
                        sb.append("Mindestgebot " + AuctionItemUtil.getReservePrice(auctionItem).orElse(Money.of(CurrencyUnit.EUR, 0)));
                    } else {
                        sb.append("Kein Mindestpreis gefordert");
                    }

                    sb.append("      " + auctionItem.getTitle());
                    sb.append(" ( " + auctionItem.getUrl() + " )");
                    sb.append("\n");
                }
            }
            case AUCTION_WILL_END_SOON_NOTIFICATION -> {
                sb.append("Für folgende(s) Fahrzeug(e) ist die Auktion in naher Zukunft beendet. Das aktuelle Gebot bzw. der Mindestpreis" +
                        ", der in Ihrem Suchauftrag eingetragen wurde, ist noch nicht erreicht worden.\n\n");

                for (AuctionItem auctionItem : auctionItems) {
                    sb.append(auctionItem.getTitle() + " derzeit bei " + AuctionItemUtil.getReservePriceOrHigherBidIfExisting(auctionItem)
                            + " ---> " + auctionItem.getUrl() + "\n");
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + notificationType);
        }

        return sb.toString();
    }

    /**
     * Returns the title for the notification
     *
     * @return
     */
    public String getNotificationTitle() {
        switch (notificationType) {
            case NEW_OLDTIMERS_ONLINE_NOTIFICATION -> {
                if (auctionItems.size() == 1) {
                    return auctionItems.get(0).getTitle() + " wird versteigert";
                }
                return "Es werden " + auctionItems.size() + " Oldtimer zu einem potentiell gutem Mindestpreis versteigert.";

            }
            case AUCTION_WILL_END_SOON_NOTIFICATION -> {
                if (auctionItems.size() == 1) {
                    return "Bei " + AuctionItemUtil.getReservePriceOrHigherBidIfExisting(auctionItems.get(0)) + " ist " + auctionItems.get(0).getTitle() + " gleich "
                            + "versteigert worden";
                }
                return auctionItems.size() + " Oldtimer sind gleich zu einem potentiell gutem Mindestpreis versteigert worden";

            }
            default -> throw new IllegalStateException("Unexpected value: " + notificationType);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        if (((Notification) o).getNotificationType() != getNotificationType()) return false;

        Notification that = (Notification) o;
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

    @Override
    public String toString() {
        return getNotificationType() + " for items " + auctionItems + "\n" + getNotificationTitle() + "\n" + "\n" + buildNotificationText();
    }
}
