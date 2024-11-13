package co.viplove.choot.poc2;

import co.viplove.choot.poc3.Neo4jResultRepository;
import co.viplove.choot.poc3.pojo.Neo4jResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User Management System")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLikesRepository userLikesRepository;

    /*@Autowired
    private LikeRepository likeRepository;*/

    @Operation(summary = "Get user by username", description = "Retrieve a user by their username", tags = { "User Management System" })
    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve", required = true) @PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @Operation(summary = "Get user by email", description = "Retrieve a user by their email", tags = { "User Management System" })
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true) @PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    @Operation(summary = "Find user by ID", description = "Retrieve a user by their unique ID", tags = { "User Management System" })
    @GetMapping("/user/{id}")
    public Optional<User> findById(
            @Parameter(description = "ID of the user to be retrieved", required = true) @PathVariable String id) {
        return userRepository.findById(id);
    }

    @Operation(summary = "Get all users", description = "Retrieve all users", tags = { "User Management System" })
    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Operation(summary = "Create a new user with email", description = "Create a new user with the provided email", tags = { "User Management System" })
    @PostMapping("/create/{email}")
    public User createUser(
            @Parameter(description = "Email of the user to create", required = true) @PathVariable String email) {
        return userRepository.save(new User(email));
    }

    @Operation(summary = "Create a new user with email and username", description = "Create a new user with the provided email and username", tags = { "User Management System" })
    @PostMapping("/create/email/{email}/username/{username}")
    public User createUser(
            @Parameter(description = "Email of the user to create", required = true) @PathVariable String email,
            @Parameter(description = "Username of the user to create", required = true) @PathVariable String username) {
        return userRepository.save(new User(email, username));
    }

    @Operation(summary = "Create a new user", description = "Create a new user with the provided user object", tags = { "User Management System" })
    @PostMapping("/create/user")
    public User createUser(
            @Parameter(description = "User object to create", required = true) @RequestBody User user) {
        return userRepository.save(user);
    }

    @Operation(summary = "Delete user by email", description = "Delete a user by their email", tags = { "User Management System" })
    @DeleteMapping("/{email}")
    public void deleteUserByUserId(
            @Parameter(description = "Email of the user to delete", required = true) @PathVariable String email) {
        userRepository.deleteById(email);
    }

    @Operation(summary = "Delete all users and likes", description = "Delete all users and likes", tags = { "User Management System" })
    @DeleteMapping("/deleteall")
    public void deleteAll() {
        userRepository.deleteAll();
        //likeRepository.deleteAll();
    }

    @Operation(summary = "Create a like relationship between users", description = "Create a like relationship between two users", tags = { "User Management System" })
    @PostMapping("/create/{email}/like/{targetemail}")
    public User createLikeRelationship(
            @Parameter(description = "Email of the user who likes", required = true) @PathVariable String email,
            @Parameter(description = "Email of the user to be liked", required = true) @PathVariable String targetemail) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        Optional<User> targetUserOpt = userRepository.findByEmail(targetemail);

        if (userOpt.isPresent() && targetUserOpt.isPresent()) {
            User user = userOpt.get();
            User targetUser = targetUserOpt.get();
            Like like = new Like();
            //like.setUser(user);
            like.setLikedUser(targetUser);
            user.setLike(like);
            //likeRepository.save(like); //creates a node. not needed.
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User or target user not found");
        }
    }

    /*@Autowired
    Neo4jResultRepository neo4jResultRepository;

    @Operation(summary = "Get all users using custom query", description = "Retrieve all users using a custom query", tags = { "User Management System" })
    @RequestMapping(value = "/custom-query/{query}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Neo4jResult> getAllUsersUsingCustomQuery(String query) {
        //StringResponse stringResponse = new StringResponse();
        //stringResponse.setResponse(
        //log.info("Result: {}",userRepository.executeQuery(query).get(0).getLikeList());
        return ResponseEntity.ok(neo4jResultRepository.executeQuery(query));
        //return stringResponse.getResponse();
    }*/

    public class StringResponse {

        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}