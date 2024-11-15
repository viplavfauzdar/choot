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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/persons")
@Slf4j
@Tag(name = "Person Controller", description = "Person Controller")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ChootMongoDbService chootMongoDbService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity.ok(createdPerson);
    }

    @PostMapping(value = "/upload/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload files for a person")
    public ResponseEntity<Person> uploadChoot(
            @Parameter(description = "Email of the person", required = true) @RequestParam String email,
            @Parameter(description = "Files to upload", required = true) @RequestParam("files") List<MultipartFile> files) throws IOException {
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
    @Operation(summary = "Get a file by its ID")
    public ResponseEntity<byte[]> getChoot(
            @Parameter(description = "ID of the file", required = true) @PathVariable String id) throws IOException {
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

    @GetMapping("/files/{email}")
    @Operation(summary = "Get all files by email")
    public ResponseEntity<byte[]> getAllChootsByEmail(
            @Parameter(description = "Email of the person", required = true) @PathVariable String email) throws IOException {
        Optional<Person> person = personService.findPersonByEmail(email);
        if (person.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<byte[]> files = new ArrayList<>();
        for (String fileId : person.get().getMongoDbObjectId()) {
            ObjectId objectId = new ObjectId(fileId);
            ChootMongoDbDocument chootMongoDbDocument = chootMongoDbService.getDocumentById(objectId);
            String contentType = chootMongoDbDocument.getMetadata().getContentType();
            log.info("CONTENT TYPE: {}", contentType);
            InputStream chootStream = chootMongoDbService.getChoot(objectId);
            files.add(chootStream.readAllBytes());
        }

        //return ResponseEntity.ok(files);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "chootMongoDbDocument.getFilename()" + ";charset=" + StandardCharsets.UTF_8.name() + "\"")
                .contentType(MediaType.parseMediaType("image/png"))
                .body(combineByteArrays(files));
    }

    public static byte[] combineByteArrays(List<byte[]> byteArrays) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] byteArray : byteArrays) {
            outputStream.write(byteArray);
        }
        return outputStream.toByteArray();
    }

    @GetMapping("/images/{email}")
    @Operation(summary = "Get all images by email")
    public ResponseEntity<List<ResponseEntity<byte[]>>> getAllImagesByEmail(
            @Parameter(description = "Email of the person", required = true) @PathVariable String email) throws IOException {
        Optional<Person> person = personService.findPersonByEmail(email);
        if (person.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseEntity<byte[]>> images = new ArrayList<>();
        for (String fileId : person.get().getMongoDbObjectId()) {
            ObjectId objectId = new ObjectId(fileId);
            ChootMongoDbDocument chootMongoDbDocument = chootMongoDbService.getDocumentById(objectId);
            InputStream chootStream = chootMongoDbService.getChoot(objectId);
            byte[] imageBytes = chootStream.readAllBytes();
            chootStream.close();

            ResponseEntity<byte[]> imageResponse = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + chootMongoDbDocument.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(chootMongoDbDocument.getMetadata().getContentType()))
                    .body(imageBytes);

            images.add(imageResponse);
        }

        return ResponseEntity.ok(images);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a person by email")
    public ResponseEntity<Person> getPersonByEmail(
            @Parameter(description = "Email of the person", required = true) @PathVariable String email) {
        Optional<Person> person = personService.findPersonByEmail(email);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all persons")
    public ResponseEntity<Iterable<Person>> getAllPersons() {
        Iterable<Person> persons = personService.findAllPersons();
        return ResponseEntity.ok(persons);
    }

    @DeleteMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a person by email")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "Email of the person to delete", required = true) @PathVariable String email) {
        personService.deletePerson(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{personEmail}/requestFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Request friendship with another person")
    public ResponseEntity<Person> requestFriendship(
            @Parameter(description = "Email of the person requesting friendship", required = true) @PathVariable String personEmail,
            @Parameter(description = "Email of the friend", required = true) @PathVariable String friendEmail) {
        Person person = personService.requestFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }

    @PostMapping(value = "/{personEmail}/acceptFriendship/{friendEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Accept friendship with another person")
    public ResponseEntity<Person> acceptFriendship(
            @Parameter(description = "Email of the person accepting friendship", required = true) @PathVariable String personEmail,
            @Parameter(description = "Email of the friend", required = true) @PathVariable String friendEmail) {
        Person person = personService.acceptFriendship(personEmail, friendEmail);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/friend/{email}/{type}")
    @Operation(summary = "Get a person by friend's email and relationship type")
    public ResponseEntity<List<Optional<Person>>> getPersonByFriend(
            @Parameter(description = "Email of the person", required = true) @PathVariable String email,
            @Parameter(description = "Type of relationship", required = true) @PathVariable RelationshipType type) {
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
    @Operation(summary = "Check the status of a friendship request/accept")
    public ResponseEntity<Person> findFriendshipStatus(
            @Parameter(description = "Email of the person", required = true) @PathVariable String emailPerson,
            @Parameter(description = "Email of the friend", required = true) @PathVariable String emailFriend,
            @Parameter(description = "Type of relationship", required = true) @PathVariable RelationshipType type) {
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