package co.viplove.choot.poc2;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends Neo4jRepository<Like, Long> {
    // Custom query methods can be added here if needed
}