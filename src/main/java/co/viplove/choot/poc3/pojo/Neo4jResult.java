package co.viplove.choot.poc3.pojo;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Node
public class Neo4jResult {

    @Id
    String email;

    @Relationship(type = "USER", direction = Relationship.Direction.OUTGOING)
    private User user;

    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Likes> likes;

    @Relationship(type = "LIKED_USERS", direction = Relationship.Direction.INCOMING)
    private List<User> likedUsers;

}