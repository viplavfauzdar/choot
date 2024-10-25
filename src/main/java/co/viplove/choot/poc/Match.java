package co.viplove.choot.poc;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/*
 * The Match class represents a match between two persons in the Neo4j database.
 * It is annotated with @Node to indicate that it is a node in the graph.
 * The @Id annotation is used to specify the unique identifier for the node.
 */
@Node
@Data
public class Match {

    //Not having below causes unsatisfied link exception. Can't have @Id for person below.
    @GeneratedValue
    @Id
    private Long id; //give it a different name to not conflict with neo4j id function
    private boolean isMatched; // Indicates if the two persons are matched
    //private Date matchDate = new Date(); // The date when the match occurred

    @Relationship(type = "LIKED_BY", direction = Relationship.Direction.INCOMING)
    private Person person; // The person who liked

    @Relationship(type = "LIKED", direction = Relationship.Direction.OUTGOING)
    private Person likedPerson; // The person who was liked

    // Additional fields can be added as needed
//    private String matchType; // The type of match (e.g., "like", "super like", etc.)
//    private String status; // The status of the match (e.g., "active", "inactive", etc.)
//    private String message; // Optional message associated with the match
//    private String additionalInfo; // Any additional information related to the match
//    private String matchId; // Unique identifier for the match
//    private String matchScore; // Score indicating the strength of the match
//    private String matchDuration; // Duration of the match
//    private String matchFeedback; // Feedback from the users about the match
//    private String matchSource; // Source of the match (e.g., "app", "web", etc.)
//    private String matchCategory; // Category of the match (e.g., "friend", "date", etc.)
//    private String matchLocation; // Location where the match occurred
//    private String matchTime; // Time when the match occurred
//    private String matchContext; // Context of the match (e.g., "swipe", "search", etc.)
//    private String matchTags; // Tags associated with the match
//    private String matchNotes; // Notes related to the match
//    private String matchImage; // Image associated with the match
//    private String matchVideo; // Video associated with the match

}
