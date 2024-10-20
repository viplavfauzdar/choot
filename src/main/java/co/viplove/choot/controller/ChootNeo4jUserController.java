package co.viplove.choot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.viplove.choot.entity.ChootNeo4jUser;
import co.viplove.choot.service.ChootNeo4jUserService;

import java.util.ArrayList;
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

    @PostMapping("/match")
    public ResponseEntity<ChootNeo4jUser> match(@RequestParam String email, @RequestParam String matchUserEmail) {
        Optional<ChootNeo4jUser> user = userService.getUserByEmail(email);
        Optional<ChootNeo4jUser> matchUser = userService.getUserByEmail(matchUserEmail);
        if (user.isPresent()) {
            ChootNeo4jUser existingUser = user.get();
            List<ChootNeo4jUser> users = existingUser.getMatchedUsers();
            if (users.isEmpty()) users = new ArrayList<>();
            matchUser.ifPresent(users::add);
            existingUser.setMatchedUsers(users);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<ChootNeo4jUser> getByUsername(@RequestParam String username) {
        Optional<ChootNeo4jUser> user = userService.getUserByEmail(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getbyemail")
    public ResponseEntity<ChootNeo4jUser> getByEmail(@RequestParam String email) {
        Optional<ChootNeo4jUser> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("getall")
    public ResponseEntity<List<ChootNeo4jUser>> getAll() {
        List<ChootNeo4jUser> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }
}