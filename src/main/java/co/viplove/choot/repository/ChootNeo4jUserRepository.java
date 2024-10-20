package co.viplove.choot.repository;

import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import co.viplove.choot.entity.ChootNeo4jUser;

@Repository
public interface ChootNeo4jUserRepository extends Neo4jRepository<ChootNeo4jUser, String> {
    Optional<ChootNeo4jUser> findByEmail(String email);
    
}
