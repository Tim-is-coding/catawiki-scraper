package de.rothenpieler.catawiki.logic.notification.searchrequest.evalutaionengine;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.searchrequest.AbstractItemSearchRequest;

import java.util.List;

/**
 * Defines the functions all search engines must be able to compute<br>
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 16.04.23
 */
public interface SearchRequestEngine<T extends AbstractItemSearchRequest> {

    public List<AuctionItem> computeSearchRequest(List<AuctionItem> itemsToBeSearchedThrough, T searchRequest);

}
