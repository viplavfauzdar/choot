package co.viplove.choot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.math.BigDecimal;

@Data
@Node
public class ChootNeo4jUser {

    // Constructor
    public ChootNeo4jUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;
    private Integer age;
    private String gender;
    private List<String> mongoDbObjectId;
    private Location location;
    private String description;
    private List<String> languages;
    private boolean deactivated;

    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Neo4JLikedUsers> likedUsers;

    @Data
    public static class Location {
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
}