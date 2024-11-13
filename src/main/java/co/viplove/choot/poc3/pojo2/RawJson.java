package co.viplove.choot.poc3.pojo2;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

@Data
public class RawJson {

    @Id
    public List<String> keys;
    public Long length;
    public List<Field> fields;
    public FieldLookup fieldLookup;

}
