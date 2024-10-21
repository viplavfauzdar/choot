package co.viplove.choot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.viplove.choot.entity.ChootNeo4jUser;
import co.viplove.choot.repository.ChootNeo4jUserRepository;

@Service
public class ChootNeo4jUserService {

    @Autowired
    private ChootNeo4jUserRepository userRepository;

    public ChootNeo4jUser createUser(String username, String email) {
        ChootNeo4jUser user = new ChootNeo4jUser(username, email);
        return userRepository.save(user);
    }

    public Optional<ChootNeo4jUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<ChootNeo4jUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public ChootNeo4jUser addLikedUser(String username, String likedUserName) {
        ChootNeo4jUser user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        ChootNeo4jUser likedUser = userRepository.findById(likedUserName).orElseThrow(() -> new RuntimeException("Liked User not found"));

        // Check for circular relationship
        boolean circularRelationshipExists = userRepository.existsRelationship(likedUserName, username);
        if (circularRelationshipExists) {
            throw new RuntimeException("Circular relationship detected");
        }

        List<ChootNeo4jUser> likedUsers = user.getLikedUsers();
        if (likedUsers == null) {
            likedUsers = new ArrayList<>();
        }
        likedUsers.add(likedUser);
        user.setLikedUsers(likedUsers);
        return userRepository.save(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteById(username);
    }
}