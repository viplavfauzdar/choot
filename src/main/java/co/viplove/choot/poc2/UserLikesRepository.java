package co.viplove.choot.poc2;

import co.viplove.choot.poc3.pojo.Neo4jResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserLikesRepository extends Neo4jRepository<UserLikes, Long> {

    @Query("MATCH (n:User {email: $query})-[r:LIKES]->(m:User) RETURN n as user, collect(r) as likes, collect(m) as likedUsers")
    Neo4jResult executeQuery(String query);

}
