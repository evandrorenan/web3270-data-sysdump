package com.evandrorenan.web3270datasysdump.framework;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB configuration class for the Web3270 Data Sysdump application.
 * This class enables MongoDB repositories and configures the MongoDB connection
 * settings for the application.
 *
 * The MongoDB connection details are configured through environment variables:
 * - SPRING_DATA_MONGODB_URI: MongoDB connection URI
 * - SPRING_DATA_MONGODB_DATABASE: Database name
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.evandrorenan.web3270datasysdump.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":" + port);
        MongoClientSettings settings = MongoClientSettings.builder()
                                                          .applyConnectionString(connectionString)
                                                          .build();
        return MongoClients.create(settings);
    }
}
