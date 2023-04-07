package de.rothenpieler.catawiki.exception;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
public class CanNotQueryBidsException extends Exception {

    public CanNotQueryBidsException(String message, Throwable cause) {
        super(message, cause);
    }

}
