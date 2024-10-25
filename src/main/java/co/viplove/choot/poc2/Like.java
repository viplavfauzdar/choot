package co.viplove.choot.poc2;

import lombok.Data;
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
    private String timestamp; // Timestamp of when the like was created


}
