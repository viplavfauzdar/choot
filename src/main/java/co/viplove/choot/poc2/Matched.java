package co.viplove.choot.poc2;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.sql.Timestamp;

@RelationshipProperties
@Data
public class Matched {

    @GeneratedValue
    @RelationshipId
    private Long id;

    @Property("matchdate")
    private String matchDate = new Timestamp(System.currentTimeMillis()).toString();

    //@Property("user")
    //private User user; // The user who liked

    //@Property("likeduser")
    @TargetNode
    private User matchedWithUser; // The user who is being matched with
    
}
