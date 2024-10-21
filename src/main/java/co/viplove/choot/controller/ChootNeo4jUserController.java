package co.viplove.choot.controller;

import co.viplove.choot.entity.ChootNeo4jUser;
import co.viplove.choot.service.ChootNeo4jUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ChootNeo4jUserController {

    @Autowired
    private ChootNeo4jUserService userService;

    @PostMapping("/create")
    public ResponseEntity<ChootNeo4jUser> createUser(@RequestParam String username, @RequestParam String email) {
        ChootNeo4jUser user = userService.createUser(username, email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/like")
    public ChootNeo4jUser likeUser(@RequestParam String username, @RequestParam String likedUser) {
        return userService.addLikedUser(username, likedUser);
    }

    @GetMapping("/getbyemail")
    public ResponseEntity<ChootNeo4jUser> getByEmail(@RequestParam String email) {
        Optional<ChootNeo4jUser> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ChootNeo4jUser>> getAll() {
        List<ChootNeo4jUser> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletebyusername")
    public ResponseEntity<Void> deleteUserByUsername(@RequestParam String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }
}