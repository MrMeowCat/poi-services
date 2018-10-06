package com.github.mrmeowcat.poi_auth.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * Configuration for MongoDB.
 */
@Configuration
@EnableMongoRepositories("com.github.mrmeowcat.poi_auth.repository")
@EnableMongoAuditing
class MongoConfig {

    /**
     * MongoDbFactory.
     */
    @Autowired
    lateinit var mongoDbFactory: MongoDbFactory
    /**
     * MongoMappingContext.
     */
    @Autowired
    lateinit var mongoMappingContext: MongoMappingContext

    /**
     * Creates MappingMongoConverter Bean.
     */
    @Bean
    fun mappingMongoConverter(): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDbFactory)
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }
}
