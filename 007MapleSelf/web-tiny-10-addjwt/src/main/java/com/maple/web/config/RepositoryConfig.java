package com.maple.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.maple.web.nosql.elasticsearch")
@EnableMongoRepositories(basePackages = "com.maple.web.nosql.mongodb")

public class RepositoryConfig {
}
