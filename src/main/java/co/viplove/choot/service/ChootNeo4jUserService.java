package co.viplove.choot.service;

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
    public ChootNeo4jUser createUser(String username, String email, List<ChootNeo4jUser> matchedUsers) {
        ChootNeo4jUser user = new ChootNeo4jUser(username, email);
        user.setMatchedUsers(matchedUsers);
        return userRepository.save(user);
    }

    @Transactional
    public void addMatchedUser(String username, ChootNeo4jUser matchedUser) {
        ChootNeo4jUser user = userRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.getMatchedUsers().add(matchedUser);
        userRepository.save(user);
    }
}
