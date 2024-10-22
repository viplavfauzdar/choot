package co.viplove.choot.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/{name}")
    public Person createPerson(@PathVariable String name) {
        return personService.createPerson(name);
    }

    @PostMapping("/{personName}/friend/{friendName}")
    public void addFriendRelationship(@PathVariable String personName, @PathVariable String friendName) {
        personService.addFriendRelationship(personName, friendName);
    }

    @GetMapping("/{name}")
    public Person findByName(@PathVariable String name) {
        return personService.findByName(name);
    }

    @GetMapping
    public Iterable<Person> findAll() {
        return personService.findAll();
    }
}

