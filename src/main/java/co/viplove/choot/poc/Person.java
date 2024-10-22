package co.viplove.choot.poc;

import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class Person {

    @Id
    private String name;

    // One person can be friends with many others
    @ToString.Exclude
    @Relationship(type = "FRIEND_OF", direction = Relationship.Direction.OUTGOING)
    private Set<Person> friends = new HashSet<>();

    public Person(String name) {
        this.name = name;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public void setFriends(Set<Person> friends) {
        this.friends = friends;
    }
}
