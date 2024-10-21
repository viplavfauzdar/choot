package co.viplove.choot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Node
@Data
public class Neo4JLikedUsers {

    /*@Id
    @GeneratedValue
    private Long id;*/

    @Id
    private String username;
    private List<String> likedUserNames;

    public Neo4JLikedUsers(String username) {
        this.username = username;
    }

    //private Map<String, List<String>> likedUsers = new HashMap<>();

}
