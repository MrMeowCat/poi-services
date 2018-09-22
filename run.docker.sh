#!/usr/bin/env bash

# build modules
#mvn package

# build and run containers
docker-compose up -d --build
