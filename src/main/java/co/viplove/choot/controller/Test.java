package co.viplove.choot.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@RestController
public class Test {

    @GetMapping("/chootde")
    public String chootDe(){
        return "Ye le :)";
    }

    @GetMapping("/chootdikha")
    public ResponseEntity<byte[]> chootDikha() throws IOException {
        // Path to the image file
        Path imagePath = Paths.get("build/tmp/choot.jpg");

        // Read the image file into a byte array
        byte[] imageBytes = Files.readAllBytes(imagePath);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpeg");

        // Return the image as a byte array
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    public static void main(String[] args) {
        HashMap<String, List<String>> likes = new HashMap<>();
        List<String> likedUsers = List.of("dingo", "john");
        likes.put("viplav", likedUsers);
        likes.put("dingo", List.of("viplav"));
        likes.put("john", List.of("dingo"));
        System.out.println(likes);
    }
    
    
}
