package co.viplove.choot.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@Slf4j
public class Neo4jConfig {

    @Value("${spring.neo4j.uri}")
    String uri;

    @Value("${spring.neo4j.username}")
    String username;

    @Value("${spring.neo4j.password}")
    String password;

    @Bean
    public Driver neo4jDriver() {
        log.info("URI: {}", uri);
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        log.info("DRIVER_: {}", driver);
        return Neo4jClient.create(driver);
    }

    @Bean
    public Neo4jMappingContext neo4jMappingContext() {
        return new Neo4jMappingContext();
    }

    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext mappingContext) {
        log.info("NEO4J_CLIENT: {} MAPPING_CONTEXT: {}", neo4jClient, mappingContext);
        return new Neo4jTemplate(neo4jClient, mappingContext);
    }

    @Bean
    public Neo4jTransactionManager transactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}
