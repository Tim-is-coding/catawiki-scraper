package de.rothenpieler.catawiki.exception;

public class CanNotSendNotificationViaEmailException extends Exception{

    public CanNotSendNotificationViaEmailException(Exception e) {
        super(e);
    }
}
