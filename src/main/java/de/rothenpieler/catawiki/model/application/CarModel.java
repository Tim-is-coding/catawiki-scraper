package de.rothenpieler.catawiki.model.application;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * A car model is a combination of a {@link CarBrand} and all technical descriptions/names
 * that reference the car of interest
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
@Getter
public enum CarModel {

    VOLKSWAGEN_KAEFER(CarBrand.VOLKSWAGEN, "1200", "1300", "1303"),
    TOYOTA_LANDCRUISER_J4(CarBrand.TOYOTA, "FJ40", "FJ42", "BJ40", "BJ42", "BJ45", "FJ45");

    private final CarBrand brand;
    private final List<String> technicalNames;

    CarModel(CarBrand volkswagen, String... technicalNames) {
        brand = volkswagen;
        this.technicalNames = Arrays.asList(technicalNames);
    }
}
