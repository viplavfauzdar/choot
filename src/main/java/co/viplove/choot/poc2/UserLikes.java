package co.viplove.choot.poc2;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Node
public class UserLikes {

    @Id
    private Long id;

    @Relationship(type = "USER", direction = Relationship.Direction.OUTGOING)
    private User user;

    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Like> likes;

    @Relationship(type = "LIKED_USERS", direction = Relationship.Direction.OUTGOING)
    private List<User> likedUsers;
}