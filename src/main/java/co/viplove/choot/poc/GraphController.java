package co.viplove.choot.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/graph")
@Slf4j
public class GraphController {

    @Autowired
    private GraphService graphService;

    @Autowired
    private MatchRepository  matchRepository;

    @PostMapping("/createperson/{name}")
    public Person createPerson(@PathVariable String name) {
        return graphService.createPerson(name);
    }

    @PostMapping("/creatematch/{personName}/match/{likedPersonName}")
    public Match createMatch(@PathVariable String personName, @PathVariable String likedPersonName) {
        return graphService.createOrUpdateMatch(personName, likedPersonName);
    }

    @GetMapping("/person/{name}")
    public Person findByName(@PathVariable String name) {
        return graphService.findByName(name);
    }

    @GetMapping("/people")
    public Iterable<Person> findAll() {
        return graphService.findAll();
    }

    //Below are only for validation purposes

    @GetMapping
    @RequestMapping("/matches/findall")
    public Iterable<Match> findAllMatches() {
        return matchRepository.findAll();
    }

    @GetMapping("/{personName}/match/{likedPersonName}")
    public Optional<List<Match>> findMatchByPersonAndLikedPerson(@PathVariable String personName, @PathVariable String likedPersonName) {
        return matchRepository.findByPersonAndLikedPerson(personName, likedPersonName);
    }

    @GetMapping("/match/person/{personName}")
    public Optional<Match> findMatchByPerson(@PathVariable String personName) {
        Optional<Match> match = matchRepository.findByPerson(personName);
        if(match.isPresent()){
            log.info("Match found: {}", match.get());
            return matchRepository.findById(match.get().getId()); // Assuming Match has an getId() method
        } else {
            log.info("No match found for person: {}", personName);
            return Optional.empty(); // or return an empty Optional
        }
    }

    //Doesn't delete everything, only the matches/relationships
    //UNWIND $__relationships__ AS relationship WITH relationship MATCH (startNode) WHERE id(startNode) = relationship.fromId MATCH (endNode) WHERE id(endNode) = relationship.toId
    // MERGE (startNode)<-[relProps:`LIKED_BY`]-(endNode) RETURN id(relProps) AS __elementId__
    @DeleteMapping("/match/deleteall")
    public void deleteAll() {
        matchRepository.deleteAll();
    }

    @GetMapping("/match/fineone/{name}")
    public Optional<Match> fineOne(@PathVariable String name) {
        return matchRepository.findByPersonByName(name);
    }

}

