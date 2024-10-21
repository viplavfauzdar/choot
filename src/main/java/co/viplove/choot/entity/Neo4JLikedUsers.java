package co.viplove.choot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Neo4JLikedUsers {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String likedUserName;

    public Neo4JLikedUsers(String username, String likedUserName) {
        this.username = username;
        this.likedUserName = likedUserName;
    }
}
