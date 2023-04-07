package de.rothenpieler.catawiki.mongodb;

import de.rothenpieler.catawiki.model.catawiki.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CRUD for {@link Auction} objects using mongoDB
 *
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 28.10.22
 */
public interface AuctionRepository extends MongoRepository<Auction, Long> {

}
