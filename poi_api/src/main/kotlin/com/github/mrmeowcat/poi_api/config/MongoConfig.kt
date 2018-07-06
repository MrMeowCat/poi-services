package com.github.mrmeowcat.poi_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories
@EnableMongoAuditing
class MongoConfig {
}