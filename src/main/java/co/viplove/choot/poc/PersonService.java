package co.viplove.choot.poc;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(String name) {
        return personRepository.save(new Person(name));
    }

    public void addFriendRelationship(String personName, String friendName) {
        Optional<Person> person = personRepository.findByName(personName);
        Optional<Person> friend = personRepository.findByName(friendName);

        if (person.isPresent() && friend.isPresent()) {
            //friend.get().setFriends(null);
            friend.get().getFriends().remove(person.get());
            person.get().getFriends().add(friend.get());
            personRepository.save(person.get());
        }
}

    public Person findByName(String name) {
        return personRepository.findByName(name).orElse(null);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }
}

