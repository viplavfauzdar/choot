package co.viplove.choot.entity;


import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.util.List;
import java.math.BigDecimal;

@Data
@Node
public class ChootNeo4jUser {

    /* @Id
    @GeneratedValue
    private Long id;

    @Property("username")
    private String username;

    @Property("email")
    private String email;
    */

    @Id
    private String username;
    private String email; 

    // Constructor
    public ChootNeo4jUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    private Integer age;
    private String gender;
    private List<String> mongoDbObjectId;
    private Location location;
    private String description;
    private List<String> languages;

    @Relationship(type = "MATCHED_WITH")
    private List<ChootNeo4jUser> matchedUsers;

    @Data
    class Location{
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
    
}
