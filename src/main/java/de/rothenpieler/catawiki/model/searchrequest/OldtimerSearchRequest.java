package de.rothenpieler.catawiki.model.searchrequest;

/**
 * Represents all possible settings for an search request for {@link SearchRequestItemType#OLDTIMER}
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 16.04.23
 */
public class OldtimerSearchRequest extends AbstractItemSearchRequest{

    private String brand; // e.g. 'Volkswagen'

    private int yearOfProduction; // the year the car was produced in. E.g. '1967'

    /*
     * If true, also accept cars that are older than the yearOfProduction.
     * Otherwise, only cars that were build in exact that year
     */
    private boolean alsoOlderModels;

    private String technicalName; // e.g. "BJ42", "1300", "X5"

}
