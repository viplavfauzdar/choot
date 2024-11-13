package co.viplove.choot.poc4.copilot;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, String> {
    // Custom query methods can be defined here if needed
    Optional<Person> findByEmail(String email);
}