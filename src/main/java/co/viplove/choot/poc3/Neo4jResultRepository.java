package co.viplove.choot.poc3;

import co.viplove.choot.poc3.pojo.Likes;
import co.viplove.choot.poc3.pojo.Neo4jResult;
import co.viplove.choot.poc3.pojo.User;
import co.viplove.choot.poc3.pojo2.RawJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public interface Neo4jResultRepository extends Neo4jRepository<RawJson, List> {

    Logger logger = LoggerFactory.getLogger(Neo4jResultRepository.class);

    //@Query("MATCH (n:User {email: 'viplav@'})-[r:LIKES]->(m:User) RETURN n as user, collect(r) as likes, collect(m) as likedUsers")
    default RawJson executeQuery(String query) throws JsonProcessingException {
        logger.info("Executing query with parameter: {}", query);
        ObjectMapper mapper = new ObjectMapper();
        RawJson result = mapper.readValue(executeQueryInternal(query),RawJson.class);
        logger.info("Query result: {}", result);
        return result;
    }

    @Query("MATCH (n:User {email: 'viplav@'})-[r:LIKES]->(m:User) RETURN n as user, collect(r) as likes, collect(m) as likedUsers")
    String executeQueryInternal(String query);


   /* @Query("MATCH (n:User {email: 'viplav@'})-[r:LIKES]->(m:User) RETURN DISTINCT n as user")
    User executeQuery(String query);*/

   /* @Query("MATCH (n:User {email: 'viplav@'})-[r:LIKES]->(m:User) RETURN (r) as likes")
    List<Likes> executeQuery(String query);*/

    /*@Query("MATCH (n:User {email: 'viplav@'})-[r:LIKES]->(m:User) RETURN (m) as likedUsers")
    List<User> executeQuery(String query);*/
}