/*
package co.viplove.choot.poc2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @GetMapping("/{id}")
    public Optional<Like> getLikeById(@PathVariable Long id) {
        return likeRepository.findById(id);
    }

    @PostMapping
    public Like createLike(@RequestBody Like like) {
        return likeRepository.save(like);
    }

    @DeleteMapping("/{id}")
    public void deleteLikeById(@PathVariable Long id) {
        likeRepository.deleteById(id);
    }
}*/
