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

import co.viplove.choot.entity.ImageDocument;
import co.viplove.choot.service.ImageService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    /* @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    } */

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file) throws IOException {
        ObjectId id = imageService.storeImage(file);
        return ResponseEntity.ok(id.toHexString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {

          if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId format");
        }
        ObjectId objectId = new ObjectId(id);
        String contentType = imageService.getDocumentById(objectId).getMetadata().getContentType();
        log.info("CONTENT TYPE: {}", contentType);
        InputStream imageStream = imageService.getImage(objectId);

        byte[] imageBytes = imageStream.readAllBytes();

        //return image as downloadable file
        MediaType mediaType = new MediaType(contentType);
        log.info("MEDIA TYPE: {}", mediaType);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + "\"")
                .contentType(mediaType)
                .body(imageBytes);

        // Set the response headers
        /* HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);

        // Return the image as a byte array
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK); */
    }

    @GetMapping("/all-images")
    public ResponseEntity<List<ImageDocument>> getAllImages() {
        List<ImageDocument> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/dropdatabase")
    public void dropDataBase(String dbName){
        imageService.dropDataBase(dbName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable String id) {
        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ObjectId format");
        }

        ObjectId objectId = new ObjectId(id);
        imageService.deleteImage(objectId);
        return ResponseEntity.noContent().build();
    }
}