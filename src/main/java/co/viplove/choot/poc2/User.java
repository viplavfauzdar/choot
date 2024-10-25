package co.viplove.choot.poc2;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.List;
import java.math.BigDecimal;

@Node
@Data
public class User {

    /* @GeneratedValue
    @Id
    private Long id; */
    //private String id = UUID.randomUUID().toString(); // Generate a UUID for the id

    /* @Property
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis()); */

    @Id
    private String name;

    @Property
    private String username; // Username of the user

    @Property
    private String email; // Email address of the user

    @Property
    private String password; // Password for authentication

    @Property
    private String firstName; // First name of the user

    @Property
    private String lastName; // Last name of the user

    @Property
    private Integer age; // Age of the user

    @Property
    private String gender; // Gender of the user

    @Property
    private String bio; // Short biography or description

    @Property
    private List<String> interests; // List of interests or hobbies

    @Property
    private Location location; // Location of the user

    @Property
    private List<String> languages; // Languages spoken by the user

    @Property
    private List<String> mongoDbObjectId; // MongoDb image storage objectIds


    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING) // Relationship to the Like entity
    private Like like; // The like relationship associated with the user

    @Data
    public static class Location {
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
}

