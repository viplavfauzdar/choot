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

    @GetMapping("/{name}")
    public Optional<User> getUserByUsername(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @PostMapping("/{name}")
    public User createUser(@PathVariable String name) {
        return userRepository.save(new User(name));
    }

    @DeleteMapping("/{name}")
    public void deleteUserByUsername(@PathVariable String name) {
        userRepository.deleteById(name);
    }

    @DeleteMapping("/deleteall")
    public void deleteAll(){
        userRepository.deleteAll();
        likeRepository.deleteAll();
    }

    @PostMapping("/{name}/like/{targetName}")
    public User createLikeRelationship(@PathVariable String name, @PathVariable String targetName) {
        Optional<User> userOpt = userRepository.findByName(name);
        Optional<User> targetUserOpt = userRepository.findByName(targetName);

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