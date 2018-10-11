#!/usr/bin/env bash

# build modules
mvn package -Dmaven.test.skip

# build and run containers
docker-compose up -d --build
