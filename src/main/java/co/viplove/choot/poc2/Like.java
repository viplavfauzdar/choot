package co.viplove.choot.poc2;

import lombok.Data;

import java.sql.Timestamp;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class Like {

    @RelationshipId
    private Long id;

    @TargetNode
    private User user; // The user who liked

    @Property
    private User likedUser; // The user who is being liked

    @Property
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Property
    private String status; // Status of the like (e.g., pending, accepted, rejected)

    @Property
    private String message; // Optional message sent with the like

    @Property
    private String location; // Location where the like was made

    @Property
    private String likeType; // Type of like (e.g., super like, normal like)

}
