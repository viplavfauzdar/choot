package co.viplove.choot.poc2;

import lombok.Data;

import java.sql.Timestamp;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Node("Liked")
@Data
public class Like {

    @GeneratedValue
    @RelationshipId
    private Long id;

    @Property("likedate")
    private String likeDate = new Timestamp(System.currentTimeMillis()).toString();

    //@Property("user")
    //private User user; // The user who liked

    //@Property("likeduser")
    @TargetNode
    private User likedUser; // The user who is being liked

    /*
    @Property
    private String status; // Status of the like (e.g., pending, accepted, rejected)

    @Property
    private String message; // Optional message sent with the like

    @Property
    private String location; // Location where the like was made

    @Property
    private String likeType; // Type of like (e.g., super like, normal like) */

}
