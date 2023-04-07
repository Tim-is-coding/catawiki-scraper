package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Date;

/**
 * Represents an auction bid
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Data
public class Bid {


    private long amount;

    private Date bidPlacedAt;

    private String bidderName;

    public Money gettAmount() {
        return Money.of(CurrencyUnit.EUR, amount);
    }

    public void settAmount(Money amount) {
        this.amount = amount.getAmountMajorLong();
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
