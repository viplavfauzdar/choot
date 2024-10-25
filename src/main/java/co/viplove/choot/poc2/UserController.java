package co.viplove.choot.poc2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        userRepository.deleteById(username);
    }
 
    @DeleteMapping("/deleteall")
    public void deleteAll(){
        userRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @PostMapping("/{name}/like/{targetName}")
    public User createLikeRelationship(@PathVariable String name, @PathVariable String targetName) {
        Optional<User> userOpt = userRepository.findByUsername(name);
        Optional<User> targetUserOpt = userRepository.findByUsername(targetName);

        if (userOpt.isPresent() && targetUserOpt.isPresent()) {
            User user = userOpt.get();
            User targetUser = targetUserOpt.get();
            Like like = new Like();
            like.setUser(targetUser);
            user.setLike(like);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User or target user not found");
        }
    }
}