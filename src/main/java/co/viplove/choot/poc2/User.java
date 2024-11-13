package co.viplove.choot.poc2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.math.BigDecimal;

@Node("AppUser") // Node label
//@Data //this is causing the stack overflow error
public class User {

    // Doesn't return the generated id??
    /* @GeneratedValue
    private Long id; // To retrieve db generated id */
    // Can't use UUID because it generates a new one every time and creates duplicate nodes
    // Will have to do additional lookup by username or email to make any CRUD operations
    /*@Id
    private String uuid = UUID.randomUUID().toString(); // Generate a UUID for the id*/

    //@JsonIgnore
    @Property("createdate")
    private String createDate = new Timestamp(System.currentTimeMillis()).toString();

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
//private String createDate = new Date().toString(); // Date when the user was created

    @Id
    @Property
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid") // doesn't do anything. Will take any string
    private String email; // Email address of the user

    //@Id
    @NotNull(message = "Username cannot be null") //dpesn't work. accepts null
    @Property("name") // Need to call it name so it displays correctly in the graph ui
    private String username; // Username of the user

    //@JsonIgnore
    @Property
    private String firstName; // First name of the user

    //@JsonIgnore
    @Property
    private String lastName; // Last name of the user

    /* @Property
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 100, message = "Age should not be greater than 100")
    private Integer age; // Age of the user

    @Property
    private String gender; // Gender of the user

    @Property
    @Size(min = 10, max = 200, message
      = "About Me must be between 10 and 200 characters") // Validate length of bio
    private String bio; // Short biography or description

    @Property
    private List<String> interests; // List of interests or hobbies

    @Property
    private Location location; // Location of the user

    @Property
    private List<String> languages; // Languages spoken by the user

    @Property
    private List<String> mongoDbObjectId; // MongoDb image storage objectIds
    */

    // No-argument constructor
    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }


    @JsonIgnore
    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING) // Relationship to the Like entity
    @ToString.Exclude
    private Like like; // The like relationship associated with the user

    //returns an empty list
    private List<Like> likeList;

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    /*@Relationship(type = "MATCHED_WITH", direction = Relationship.Direction.INCOMING) // Relationship to the Matched entity
    @ToString.Exclude
    private Matched matchedWith; // The match relationship associated with the user*/

    /* @Data
    public static class Location {
        private BigDecimal latitude;
        private BigDecimal longitude;
    } */

    public @NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotNull(message = "Username cannot be null") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(message = "Username cannot be null") String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }
}

