package de.rothenpieler.catawiki.model.notification;

/**
 * For each type of notification there will be a separate record.
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 09.04.23
 */
public enum NotificationType {

    NEW_OLDTIMERS_ONLINE_NOTIFICATION,
    AUCTION_WILL_END_SOON_NOTIFICATION;

    @Override
    public String toString() {

        switch (this) {
            case NEW_OLDTIMERS_ONLINE_NOTIFICATION -> {
                return "'New Oldtimers Online' Notification";
            }
            case AUCTION_WILL_END_SOON_NOTIFICATION -> {
                return "'Auction will end soon' Notification";
            }
            default -> throw new IllegalStateException("unknown state " + this);
        }
    }
}
