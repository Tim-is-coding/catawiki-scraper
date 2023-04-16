package de.rothenpieler.catawiki.logic.notification.searchrequest.evalutaionengine;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.searchrequest.OldtimerSearchRequest;

import java.util.List;

/**
 * Can be used to compute {@link de.rothenpieler.catawiki.model.searchrequest.OldtimerSearchRequest}s
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 16.04.23
 */
public class OldtimerSearchRequestEngine implements SearchRequestEngine<OldtimerSearchRequest> {

    @Override
    public List<AuctionItem> computeSearchRequest(List<AuctionItem> itemsToBeSearchedThrough, OldtimerSearchRequest searchRequest) {
        return null;
    }
}
