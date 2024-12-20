package co.viplove.choot.entity;

import lombok.Data;
import lombok.ToString;
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
    private String username;
    private String email;
    private Integer age;
    private String gender;
    private List<String> mongoDbObjectId;
    private LocationOld locationOld;
    private String description;
    private List<String> languages;
    private boolean deactivated;

    //@ToString.Exclude
    @Relationship(type = "LIKED", direction = Relationship.Direction.OUTGOING)
    private List<ChootNeo4jUser> likedUsers;

    private List<String> matchedUsers; // user object causes  circular relationship

    @Data
    public static class LocationOld {
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
}