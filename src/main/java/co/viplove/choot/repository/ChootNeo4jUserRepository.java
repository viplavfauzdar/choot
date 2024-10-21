package co.viplove.choot.repository;

import co.viplove.choot.entity.ChootNeo4jUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChootNeo4jUserRepository extends Neo4jRepository<ChootNeo4jUser, String> {

    Optional<ChootNeo4jUser> findByEmail(String email);

    @Query("MATCH (a:ChootNeo4jUser)-[r:LIKED]->(b:ChootNeo4jUser) WHERE a.username = $0 AND b.username = $1 RETURN COUNT(r) > 0")
    boolean existsRelationship(String username1, String username2);
}