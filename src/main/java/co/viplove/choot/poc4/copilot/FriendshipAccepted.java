package co.viplove.choot.poc4.copilot;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.sql.Timestamp;

@Data
@RelationshipProperties
public class FriendshipAccepted {

    @Id
    @GeneratedValue
    @RelationshipId
    private Long id;

    private String acceptedDate = new Timestamp(System.currentTimeMillis()).toString();

    @TargetNode
    private Person friend;

}