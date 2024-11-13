package co.viplove.choot.poc4.copilot;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import java.sql.Timestamp;

@Data
@RelationshipProperties
public class FriendshipRequested {

    @Id
    @GeneratedValue
    @RelationshipId
    private Long id;

    private String requestedDate = new Timestamp(System.currentTimeMillis()).toString();

    @TargetNode
    private Person friend;

}