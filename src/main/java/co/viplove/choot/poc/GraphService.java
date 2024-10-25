package co.viplove.choot.poc;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class GraphService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MatchRepository matchRepository;

    public Person createPerson(String name) {
        return personRepository.save(new Person(name));
    }

    // This method removes duplicates from the match list. Duplicates happen because of LIKE & LIKED_BY relationships.
    public Match removeDuplicatesAndGetMatch(String personName1, String personName2){
        Optional<List<Match>> matchList = matchRepository.findByPersonAndLikedPerson(personName1, personName2);
        Optional<Match> match;
        if(matchList.isPresent() && !matchList.get().isEmpty()) {
            match = Optional.of(matchList.get().get(0));
            return match.get();
        }
        return null;
    }

    // This method creates or updates a match between two persons.
    public Match createOrUpdateMatch(String personName, String likedPersonName) {
        //Should I be checking? Unnecessary look up?
        Optional<Person> person = personRepository.findByName(personName);
        Optional<Person> likedPerson = personRepository.findByName(likedPersonName);

        if (person.isPresent() && likedPerson.isPresent()) { // Check if both persons exist
            Match match = removeDuplicatesAndGetMatch(likedPersonName, personName); // Remove duplicates
            if(match!=null){
                // If match already exists, update it. This means that the LIKED person liked the LIKE_BY person. So it's a match
                match.setMatched(true);
            }else{
                //this is a new like so create a new one way match
                match = new Match();
                match.setPerson(person.get());
                match.setLikedPerson(likedPerson.get());
            }

            matchRepository.save(match); // Save the match
            return match;
        }
        return null;
    }

    public Person findByName(String name) {
        return personRepository.findByName(name).orElse(null);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
}

