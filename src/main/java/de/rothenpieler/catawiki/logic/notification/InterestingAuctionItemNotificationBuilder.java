package de.rothenpieler.catawiki.logic.notification;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import de.rothenpieler.catawiki.logic.util.AuctionItemUtil;
import de.rothenpieler.catawiki.model.application.CarSearchRequest;
import de.rothenpieler.catawiki.model.catawiki.AuctionItem;
import de.rothenpieler.catawiki.model.catawiki.Bid;
import de.rothenpieler.catawiki.mongodb.AuctionItemRepository;
import de.rothenpieler.catawiki.mongodb.AuctionRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author tim.rothenpieler.extern@bdr.de
 * @project FDZ
 * @date 02.10.2022
 */
@Slf4j
@Service
public class InterestingAuctionItemNotificationBuilder {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionItemRepository auctionItemRepository;


    @Value("${spring.data.mongodb.uri}")
    private String dbConnectionUrl;


    //public List<AuctionItem> findCarsThat;

    /*
    - In 1 Stunde vorbei
    - Minimalpreis erreicht
    - Expertenbewertung einbeziehen? --> Gucken wie es bei historischen Daten aussieht
     */

    /*
    https://www.catawiki.com/buyer/api/v3/lots/62152943/bidding_block?currency_code=EUR&locale=en
     */

    /**
     * @return
     */
    public List<AuctionItem> findMatchesForSearchRequests() {

        List<AuctionItem> matches = new ArrayList<>();

        // create connection to mongodb database
        ConnectionString connectionString = new ConnectionString(dbConnectionUrl);
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).register(Bid.class).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoCollection<AuctionItem> mongoCollection;
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {


            mongoCollection = mongoClient.getDatabase("catawiki").getCollection("auctionItem", AuctionItem.class);
            mongoCollection.createIndex(Indexes.text("title"));


            // now query database for each search requests
            for (CarSearchRequest searchRequest : CarSearchRequest.values()) {

                // STEP 1: Filter by title criteria

                Set<AuctionItem> auctionItemsWithMatchingName = new HashSet<>();
                for (String modelName : searchRequest.getCarModel().getTechnicalNames()) {

                    // car title pattern: <BRAND> - <TECHNICAL MODEL NAME> - <YEAR>
                    String searchQuery = searchRequest.getCarModel().getBrand() + " - " + modelName;

                    Bson tileFilter = Filters.text("\"" + searchQuery + "\"");
                    FindIterable<AuctionItem> carsWithMatchingTitle = mongoCollection.find(tileFilter);
                    MongoCursor<AuctionItem> iterator = carsWithMatchingTitle.iterator();
                    while (iterator.hasNext()) {
                        auctionItemsWithMatchingName.add(iterator.next());
                    }


                    //List<AuctionItem> users = auctionItemRepository.find(query, AuctionItem.class);
                    //auctionItemRepository.findB

                    List<AuctionItem> matchingAuctionItems = auctionItemRepository.findByTitleContaining(searchQuery);
                    auctionItemsWithMatchingName.addAll(matchingAuctionItems);
                }

                // STEP 2: Check if price is too high
                for (AuctionItem auctionItem : auctionItemsWithMatchingName) {
                    Optional<Money> reservePrice = AuctionItemUtil.getReservePrice(auctionItem);
                    if (reservePrice.isPresent()) {
                        boolean reservePriceNotTooHigh = reservePrice.get().isLessThan(searchRequest.getMaxPrice());
                        if (reservePriceNotTooHigh) {
                            matches.add(auctionItem);
                        }
                    } else {
                        log.warn("No reserve price set? AuctionItem=" + auctionItem);
                    }
                }
            }

            return new ArrayList<>(matches);
        }
    }

}
