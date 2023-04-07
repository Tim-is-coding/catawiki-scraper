package de.rothenpieler.catawiki.model.catawiki;

import lombok.Data;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Data
public class ExpertEstimate {

    private long minimumEstimate;
    private long maximumEstimate;
    private Expert expert;

    public Money getMaximumEstimate() {
        return Money.of(CurrencyUnit.EUR, maximumEstimate);
    }

    public void setMaximumEstimate(Money maximumEstimate) {
        this.maximumEstimate = maximumEstimate.getAmountMajorLong();
    }

    public Money getMinimumEstimate() {
        return Money.of(CurrencyUnit.EUR, minimumEstimate);
    }

    public void setMinimumEstimate(Money minimumEstimate) {
        this.minimumEstimate = minimumEstimate.getAmountMajorLong();
    }


}
