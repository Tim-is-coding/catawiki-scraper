# Scraping the Catawiki.com API for oldtimers

This software uses the API of catawiki.com to query for auctions.
While the code can easily be rewritten to query all types of auctions, currently only oldtimers being queried.

Search requests can be defined and the software will automatically send an email when necessary.

## Prerequisites

1. [x] `docker` installed on your machine
2. [x] `docker-compose` installed on your machine
3. [x] `mvn` installed on your machine
4. [x] A Gmail account with API access activates and your API key

## How to start the application

The application with all its components is defined in one dockerfile-compose file.
The following command can be used to automatically start and configure the application.

**Linux or mac terminal:**

```
$ chmod a+rx start.sh
$ sudo ./start.sh
```

**Windows CMD:**

```
mvn package
docker-compose build
docker-compose up
```

Press Ctrl + C to exit the application.

This setup is intended to start a demon process that is running 24/7 on a server.

## Technical Documentation

### Network

*TODO: Screenshot of all components*

### Database

The software uses a NoSQL MongoDB. The database is running in an own docker image that automatically installs the latest
version of MongoDB.

All data loaded into the database can be inspected using MongoDB Compass. Below is an example of the "auction" document
schema.

![mongo_compass_example.png](documentation%2Fmongo_compass_example.png)
*Screenshot taken from MongoDB Compass. The tool automatically visualises the data.*

### Email notification service

The user can define search requests for specific oldtimer types, The software will check periodically if there are any
cars in the database that match one or more search request.
The notifications that are being send via E-Mail currently include:

- Notification if one or more new cars come online that are in a search request
- Notification when a car is about to be sold below search request price

Below is one example of the email for each notification type:

*TODO: Email screenshots*