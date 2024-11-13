package co.viplove.choot.poc2;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String>{
    Optional<User> findByUsername(String username);
    // Additional query methods can be defined here
    Optional<User> findByEmail(String email);

    @Query("MATCH (n:User {email: $query})-[r:LIKES]->(m:User) RETURN n as user, collect(r) as likes, collect(m) as likedUsers")
    UserLikes executeQuery(String query);


}