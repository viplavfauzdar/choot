package co.viplove.choot.poc4.copilot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Node
//@Data
public class Person {

    /*@Id
    @GeneratedValue
    private Long personId;*/

    private String createDate = new Timestamp(System.currentTimeMillis()).toString();
    private String name;
    @Id
    private String email;
    private String firstName;
    private String lastName;

    //@ToString.Exclude //still thorws stack overflow error because of nesting
    @JsonIgnore
    @Relationship(type = "FRIENDSHIP_REQUESTED", direction = Relationship.Direction.OUTGOING)
    private FriendshipRequested requestedFriends;

    //@ToString.Exclude
    @JsonIgnore
    @Relationship(type = "FRIENDSHIP_ACCEPTED", direction = Relationship.Direction.OUTGOING)
    private FriendshipAccepted acceptedFriends;

    private List<String> requestedFriendsName = new ArrayList<>();
    private List<String> acceptedFriendsName = new ArrayList<>();

    // No-argument constructor
    public Person() {
    }

    // No-argument constructor
    public Person(String name) {
        this.name = name;
    }


   /* public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }*/

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public FriendshipRequested getRequestedFriends() {
        return requestedFriends;
    }

    public void setRequestedFriends(FriendshipRequested requestedFriends) {
        this.requestedFriends = requestedFriends;
    }

    public FriendshipAccepted getAcceptedFriends() {
        return acceptedFriends;
    }

    public void setAcceptedFriends(FriendshipAccepted acceptedFriends) {
        this.acceptedFriends = acceptedFriends;
    }

    public List<String> getRequestedFriendsName() {
        return requestedFriendsName;
    }

    public void setRequestedFriendsName(List<String> requestedFriendsName) {
        this.requestedFriendsName = requestedFriendsName;
    }

    public List<String> getAcceptedFriendsName() {
        return acceptedFriendsName;
    }

    public void setAcceptedFriendsName(List<String> acceptedFriendsName) {
        this.acceptedFriendsName = acceptedFriendsName;
    }
}