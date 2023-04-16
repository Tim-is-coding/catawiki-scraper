package de.rothenpieler.catawiki.model.searchrequest;

import de.rothenpieler.catawiki.model.user.User;
import lombok.Getter;

import java.util.Date;

/**
 * A search request can be created by a user in order to receive
 * specific types of notifications for objects on catawiki
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 16.04.23
 */
@Getter
public class SearchRequest {

    // the item type of the search request (e.g. oldtimers)
    private SearchRequestItemType searchRequestItemType;

    private String searchRequestTitle;

    private Date createdAt;

    private User createdBy;
}
