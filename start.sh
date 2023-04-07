#!/bin/sh
mvn package
docker-compose build
docker-compose up