package co.viplove.choot.poc4.copilot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity.ok(createdPerson);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPersonByEmail(@PathVariable String email) {
        Optional<Person> person = personService.findPersonByEmail(email);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Person>> getAllPersons() {
        Iterable<Person> persons = personService.findAllPersons();
        return ResponseEntity.ok(persons);
    }

    @DeleteMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePerson(@PathVariable String email) {
        personService.deletePerson(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personEmail}/requestFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> requestFriendship(@PathVariable String personEmail, @PathVariable String friendEmail) {
        Person person = personService.requestFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }

    @PostMapping(value = "/{personEmail}/acceptFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> acceptFriendship(@PathVariable String personEmail, @PathVariable String friendEmail) {
        Person person = personService.acceptFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }
}