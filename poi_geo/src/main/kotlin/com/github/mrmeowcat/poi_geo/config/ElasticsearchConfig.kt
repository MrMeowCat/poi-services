package com.github.mrmeowcat.poi_geo.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories(basePackages = ["com.github.mrmeowcat.poi_geo.repository"])
class ElasticsearchConfig {
}