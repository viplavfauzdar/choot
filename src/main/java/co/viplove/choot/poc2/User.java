package co.viplove.choot.poc2;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Data
public class User {

    @Id
    private String name; // Unique identifier for the User. Need to call it 'name' to show up in the graph.

    public User(String name) {
        this.name = name;
    }

    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING) // Relationship to the Like entity
    private Like like; // The like relationship associated with the user
}
