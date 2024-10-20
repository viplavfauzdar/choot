package co.viplove.choot.entity;


import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Data
public class ChootNeo4jUser {

    private String userId;
    private String email;
    private Integer age;
    private String gender;
    private String mongoDbObjectId;
    private Location location;
    private List<String> matchUserIds;

    @Data
    class Location{
        private BigDecimal latitude;
        private BigDecimal longitude;
    }
    
}
