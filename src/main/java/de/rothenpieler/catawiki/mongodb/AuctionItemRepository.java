package de.rothenpieler.catawiki.mongodb;

import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * MongoDB repo for {@link AuctionItem}s
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
public interface AuctionItemRepository extends MongoRepository<AuctionItem, Long> {

    //db.users.findOne({"username" : /.*son.*/i});
    List<AuctionItem> findByTitleContaining(String carModelName);

    @Query("{\"title\" : /.*?0.*/i}")
    List<AuctionItem> findBySearchRequest(final @NonNull String carBrand, final @NonNull String carModelName);

}
