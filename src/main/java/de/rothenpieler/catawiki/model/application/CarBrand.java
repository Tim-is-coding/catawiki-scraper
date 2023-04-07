package de.rothenpieler.catawiki.model.application;

import lombok.NonNull;

/**
 * Keeps track of all currently interesting car brands
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
public enum CarBrand {

    VOLKSWAGEN("Volkswagen"), TOYOTA("Toyota");

    private final String brandName;

    CarBrand(final @NonNull String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}
