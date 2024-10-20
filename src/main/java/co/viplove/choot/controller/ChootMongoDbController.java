package co.viplove.choot.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import co.viplove.choot.entity.ChootMongoDbDocument;
import co.viplove.choot.service.ChootMongoDbService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/choot")
@Slf4j
public class ChootMongoDbController {

    @Autowired
    private ChootMongoDbService chootMongoDbService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadChoot(@RequestBody MultipartFile file) throws IOException {
        ObjectId id = chootMongoDbService.storeChoot(file);
        return ResponseEntity.ok(id.toHexString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getChoot(@PathVariable String id) throws IOException {

        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId format");
        }
        ObjectId objectId = new ObjectId(id);
        ChootMongoDbDocument chootMongoDbDocument = chootMongoDbService.getDocumentById(objectId);
        String contentType = chootMongoDbDocument.getMetadata().getContentType();
        
        log.info("CONTENT TYPE: {}", contentType);
        InputStream ChootStream = chootMongoDbService.getChoot(objectId);

        byte[] ChootBytes = ChootStream.readAllBytes();

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + chootMongoDbDocument.getFilename() + "\"")
            //.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + ChootDocument.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType)) // Fix: Create MediaType object using contentType string
            .body(ChootBytes);

    }

    @GetMapping("/all-choots")
    public ResponseEntity<List<ChootMongoDbDocument>> getAllChoots() {
        List<ChootMongoDbDocument> Choots = chootMongoDbService.getAllChoots();
        return ResponseEntity.ok(Choots);
    }

    @DeleteMapping("/delete/dbname")
    public void dropDataBase(String dbName){
        chootMongoDbService.dropDataBase(dbName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChoot(@PathVariable String id) {
        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId format");
        }

        ObjectId objectId = new ObjectId(id);
        chootMongoDbService.deleteChoot(objectId);
        return ResponseEntity.noContent().build();
    }
}