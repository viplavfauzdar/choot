package co.viplove.choot.poc;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Match entity.
 */
@Repository
public interface MatchRepository extends Neo4jRepository<Match, Long> {

    //Can also have r:LIKED or r:LIKED_BY
    //returns two identical rows becaue of above
    @Query("MATCH (p:Person)-[r]->(m:Match) WHERE p.name= $personName RETURN m.isMatched,p.name " +
            "UNION " +
            "MATCH (p:Person)<-[r]-(m:Match) WHERE p.name= $likedPersonName RETURN m.isMatched,p.name")
    Optional<List<Match>> findByPersonAndLikedPerson(String personName, String likedPersonName);

    //Returns half the match meaning from 1st person-relationship-match but not the 2nd person
    //@Query("MATCH (p:Person {name: $personName})-[r]-(m:Match) RETURN p,r,m") //another way of setting where clause
    @Query("MATCH (p:Person)-[r]-(m:Match) WHERE p.name = $personName RETURN p,r,m")
    Optional<Match> findByPerson(String personName);

    @Query("MATCH (p:Person {name: $name})-[r]-(match:Match) RETURN match{.isMatched, __nodeLabels__: labels(match), __internalNeo4jId__: id(match), __elementId__: id(match), " +
            "Match_LIKED_Person_true: [(match)-[:`LIKED`]->(match_likedPerson:`Person`) | match_likedPerson{.name, __nodeLabels__: labels(match_likedPerson), __elementId__: id(match_likedPerson)}], " +
            "Match_LIKED_BY_Person_false: [(match)<-[:`LIKED_BY`]-(match_person:`Person`) | match_person{.name, __nodeLabels__: labels(match_person), __elementId__: id(match_person)}]}")
    Optional<Match> findByPersonByName(String name);

    /**
     * CQL Tips
     * Can use arrows -> <- for LIKED and LIKED_BY relationships or relationship type and use hyphen -
     * MATCH (p:Person {name: 'viplav'})-[r:LIKED_BY]->(m:Match) RETURN *  //returns all matches for a LIKED_BY person
     * MATCH (p:Person {name: 'dingo'})-[r:LIKED]-(m:Match) RETURN *  //returns all matches for a LIKED person
     *
     * CQL to return only one row and not use UNION like above
     * leave generated labels as is otherwise it doesn't map to the Match pojo
     * MATCH (p:Person {name: $name})-[r]-(match:`Match`)
     * 	RETURN match{
     * 		.isMatched, __nodeLabels__: labels(match), __internalNeo4jId__: id(match), elementId__: id(match),
     * 	    Match_LIKED_BY_Person: [(match)<-[:`LIKED_BY`]-(match_person:`Person`) 	|
     * 	    match_person{.name, __nodeLabels__: labels(match_person), __elementId__: id(match_person)}],
     * 	    Match_LIKED_Person: [(match)-[:`LIKED`]->(match_likedPerson:`Person`) |
     * 	    match_likedPerson{.name, __nodeLabels__: labels(match_likedPerson), __elementId__: id(match_likedPerson)}]
     * 	}
     */

}