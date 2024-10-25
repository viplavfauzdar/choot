package co.viplove.choot.poc;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/*

 */
@Node
@Data
public class Person {

    @Id
    private String name;

    public Person(String name) {
        this.name = name;
    }

}