
package co.viplove.choot.poc3.pojo;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class Likes {

    @Id
    public Long identity;
    public String likedate;
    public Long start;
    public Long end;
    public String type;
    public Properties__1 properties;
    public String elementId;
    public String startNodeElementId;
    public String endNodeElementId;

}
