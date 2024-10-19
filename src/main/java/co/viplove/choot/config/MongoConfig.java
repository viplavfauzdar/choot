package co.viplove.choot.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${choot.database-name}")
    private String databaseName;

    
    //private MongoDatabase mongoDatabase;
    
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(){
        MongoDatabaseFactory mongoDatabaseFactory;
        mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(), databaseName);
        return mongoDatabaseFactory;
    }

    /* @Bean
    public MongoDatabase mongoDatabase(){
        mongoDatabase = mongoDatabaseFactory.getMongoDatabase("choot");
        return mongoDatabase;
    }

    @Bean
    public GridFSBucket gridFSBucket(){
        return GridFSBuckets.create(mongoDatabase);
    } */
}