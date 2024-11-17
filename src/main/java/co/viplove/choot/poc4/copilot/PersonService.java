package co.viplove.choot.poc4.copilot;

import co.viplove.choot.poc4.copilot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    /*public Optional<Person> findPersonById(Long id) {
        return personRepository.findById(id);
    }*/

    public Optional<Person> findPersonByEmail(String email) {
        Person person = personRepository.findByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, email + " not found"));
        return Optional.of(person);
    }

    public Iterable<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public void deletePerson(String email) {
        personRepository.deleteById(email);
    }

    public Person requestFriendship(String personEmail, String friendEmail) {
        Optional<Person> personOpt = personRepository.findByEmail(personEmail);
        Optional<Person> friendOpt = personRepository.findByEmail(friendEmail);

        if (personOpt.isPresent() && friendOpt.isPresent()) {
            Person person = personOpt.get();
            Person friend = friendOpt.get();
            FriendshipRequested friendshipRequested = new FriendshipRequested();
            friendshipRequested.setFriend(friend);
            person.setRequestedFriends(friendshipRequested);
            //person.getRequestedFriendsName().add(friend.getName());
            return personRepository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person or friend not found");
        }
    }

    public Person acceptFriendship(String personEmail, String friendEmail) {
        Optional<Person> personOpt = personRepository.findByEmail(personEmail);
        Optional<Person> friendOpt = personRepository.findByEmail(friendEmail);

        if (personOpt.isPresent() && friendOpt.isPresent()) {
            Person person = personOpt.get();
            Person friend = friendOpt.get();
            FriendshipAccepted friendshipAccepted = new FriendshipAccepted();
            friendshipAccepted.setFriend(friend);
            person.setAcceptedFriends(friendshipAccepted);
            //person.getAcceptedFriendsName().add(friend.getName());
            return personRepository.save(person);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person or friend not found");
        }
    }

    public List<Optional<Person>> findByFriendRequested(String email) {
        return personRepository.findByFriendRequested(email);
    }

    public List<Optional<Person>> findByFriendAccepted(String email) {
        return personRepository.findByFriendAccepted(email);
    }

    public Optional<Person> findByAlreadyRequested(String emailPerson, String emailFriend) {
        return personRepository.findByAlreadyRequested(emailPerson, emailFriend);
    }

    public Optional<Person> findByAlreadyAccepted(String emailPerson, String emailFriend) {
        return personRepository.findByAlreadyAccepted(emailPerson, emailFriend);
    }
}