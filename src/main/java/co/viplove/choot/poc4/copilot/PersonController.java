package co.viplove.choot.poc4.copilot;

import co.viplove.choot.entity.ChootMongoDbDocument;
import co.viplove.choot.service.ChootMongoDbService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/persons")
@Slf4j
@Api(value = "Person Controller", tags = {"Person Controller"})
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ChootMongoDbService chootMongoDbService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity.ok(createdPerson);
    }

    @PostMapping(value = "/upload/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Upload files for a person")
    public ResponseEntity<Person> uploadChoot(@RequestParam String email, @RequestParam("files") List<MultipartFile> files) throws IOException {
        Optional<Person> person = personService.findPersonByEmail(email);
        for (MultipartFile file : files) {
            ObjectId oid = chootMongoDbService.storeChoot(file);
            if (person.isPresent()) {
                person.get().getMongoDbObjectId().add(oid.toHexString());
                personService.createPerson(person.get());
            }
        }
        return ResponseEntity.ok(person.get());
    }

    @GetMapping("/file/{id}")
    @ApiOperation(value = "Get a file by its ID")
    public ResponseEntity<byte[]> getChoot(@PathVariable String id) throws IOException {
        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId format");
        }
        ObjectId objectId = new ObjectId(id);
        ChootMongoDbDocument chootMongoDbDocument = chootMongoDbService.getDocumentById(objectId);
        String contentType = chootMongoDbDocument.getMetadata().getContentType();

        log.info("CONTENT TYPE: {}", contentType);
        InputStream chootStream = chootMongoDbService.getChoot(objectId);

        byte[] chootBytes = chootStream.readAllBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + chootMongoDbDocument.getFilename() + ";charset=" + StandardCharsets.UTF_8.name() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(chootBytes);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a person by email")
    public ResponseEntity<Person> getPersonByEmail(@PathVariable String email) {
        Optional<Person> person = personService.findPersonByEmail(email);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all persons")
    public ResponseEntity<Iterable<Person>> getAllPersons() {
        Iterable<Person> persons = personService.findAllPersons();
        return ResponseEntity.ok(persons);
    }

    @DeleteMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete a person by email")
    public ResponseEntity<Void> deletePerson(@PathVariable String email) {
        personService.deletePerson(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personEmail}/requestFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Request friendship with another person")
    public ResponseEntity<Person> requestFriendship(@PathVariable String personEmail, @PathVariable String friendEmail) {
        Person person = personService.requestFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }

    @PostMapping(value = "/{personEmail}/acceptFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Accept friendship with another person")
    public ResponseEntity<Person> acceptFriendship(@PathVariable String personEmail, @PathVariable String friendEmail) {
        Person person = personService.acceptFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/friend/{email}/{type}")
    @ApiOperation(value = "Get a person by friend's email and relationship type")
    public ResponseEntity<List<Optional<Person>>> getPersonByFriend(@PathVariable String email, @PathVariable RelationshipType type) {
        if (type.equals(RelationshipType.REQUESTED)) {
            List<Optional<Person>> persons = personService.findByFriendRequested(email);
            return ResponseEntity.ok(persons);
        } else if (type.equals(RelationshipType.ACCEPTED)) {
            List<Optional<Person>> persons = personService.findByFriendAccepted(email);
            return ResponseEntity.ok(persons);
        }
        return null;
    }

    enum RelationshipType {
        REQUESTED,
        ACCEPTED
    }

    @GetMapping("/friend/status/{emailPerson}/{emailFriend}/{type}")
    @ApiOperation(value = "Check the status of a friendship request/accept")
    public ResponseEntity<Person> findFriendshipStatus(@PathVariable String emailPerson, @PathVariable String emailFriend, @PathVariable RelationshipType type) {
        Optional<Person> person;
        if (type.equals(RelationshipType.REQUESTED)) {
            person = personService.findByAlreadyRequested(emailPerson, emailFriend);
        } else if (type.equals(RelationshipType.ACCEPTED)) {
            person = personService.findByAlreadyAccepted(emailPerson, emailFriend);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
