package com.github.mrmeowcat.poi_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Configuration for MongoDB.
 */
@Configuration
@EnableMongoRepositories("com.github.mrmeowcat.poi_api.repository")
@EnableMongoAuditing
class MongoConfig
