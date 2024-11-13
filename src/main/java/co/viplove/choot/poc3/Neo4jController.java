package co.viplove.choot.poc3;

import co.viplove.choot.poc3.pojo.Likes;
import co.viplove.choot.poc3.pojo.User;
import co.viplove.choot.poc3.pojo2.RawJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jClient.MappingSpec;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/neo4j")
@Slf4j
public class Neo4jController {

    @Autowired
    Neo4jResultRepository neo4jResultRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    @Operation(summary = "Get all users using custom query", description = "Retrieve all users using a custom query", tags = { "User Management System" })
    @RequestMapping(value = "/query/{email}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLikesDTO> getAllUsersUsingCustomQuery(@PathVariable String email) {
       return (List<UserLikesDTO>) getAllUsersUsingCustomQuery1(email);
    }

    public Collection<UserLikesDTO> getAllUsersUsingCustomQuery1(String email) {
        String query = "MATCH (n:User {name: $email})-[r]-(m:User) " +
                       "RETURN n.name as name, r.likedate as likedate, m.name as likename";

        return neo4jClient.query(query)
                          .bind(email).to("email")
                          .fetchAs(UserLikesDTO.class)
                          .mappedBy((typeSystem, rec) -> {
                              UserLikesDTO dto = new UserLikesDTO();
                              dto.setName(rec.get("name").asString());
                              dto.setLikedate(rec.get("likedate").asString());
                              dto.setLikename(rec.get("likename").asString());
                              return dto;
                          })
                          .all();
    }
}


@Data
class UserLikesDTO {
    private String name;
    private String likedate;
    private String likename;
}
