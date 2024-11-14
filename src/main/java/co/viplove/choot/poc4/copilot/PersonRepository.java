package co.viplove.choot.poc4.copilot;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, String> {
    // Custom query methods can be defined here if needed
    Optional<Person> findByEmail(String email);

    //@Query("MATCH (p:Person {email: $email})-[r:FRIENDSHIP_REQUESTED]-(f:Person) RETURN p.name as name, p.email as email, r.requestedDate as requestedDate, r.acceptedDate as acceptedDate, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    @Query("MATCH (p:Person {email: $email})-[r:FRIENDSHIP_REQUESTED]-(f:Person) RETURN p.name as name, p.email as email, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    List<Optional<Person>> findByFriendRequested(String email);

    //@Query("MATCH (p:Person {email: $email})-[r:FRIENDSHIP_ACCEPTED]-(f:Person) RETURN p.name as name, p.email as email, r.requestedDate as requestedDate, r.acceptedDate as acceptedDate, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    @Query("MATCH (p:Person {email: $email})-[r:FRIENDSHIP_ACCEPTED]-(f:Person) RETURN p.name as name, p.email as email, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    List<Optional<Person>> findByFriendAccepted(String email);

    @Query("MATCH (p:Person {email: $emailPerson})-[r:FRIENDSHIP_REQUESTED]-(f:Person {email: $emailFriend}) RETURN p.name as name, p.email as email, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    Optional<Person> findByAlreadyRequested(String emailPerson, String emailFriend);

    @Query("MATCH (p:Person {email: $emailPerson})-[r:FRIENDSHIP_ACCEPTED]-(f:Person {email: $emailFriend}) RETURN p.name as name, p.email as email, f.name as friendName, f.email as friendEmail, r.requestedDate as friendRequestedDate, r.acceptedDate as friendAcceptedDate")
    Optional<Person> findByAlreadyAccepted(String emailPerson, String emailFriend);
}