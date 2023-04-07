package de.rothenpieler.catawiki.model.application;

import lombok.Getter;
import lombok.NonNull;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Keeps track of all cars that are currently of interest y
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
@Getter
public enum CarSearchRequest {

    KAEFER(CarModel.VOLKSWAGEN_KAEFER, Money.of(CurrencyUnit.EUR, 25000)),
    J4(CarModel.TOYOTA_LANDCRUISER_J4, Money.of(CurrencyUnit.EUR, 40000));

    private final CarModel carModel;
    private final Money maxPrice;

    CarSearchRequest(final @NonNull CarModel carModel, final @NonNull Money maxPrice) {
        this.carModel = carModel;
        this.maxPrice = maxPrice;
    }
}
