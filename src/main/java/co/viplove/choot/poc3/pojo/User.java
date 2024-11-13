
package co.viplove.choot.poc3.pojo;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

@Data
@Node
public class User {

    @Id
    public Long identity;
    public String name;
    public String email;
    private String createdate;
    public List<String> labels;
    public Properties properties;
    public String elementId;

}
