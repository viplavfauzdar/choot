package co.viplove.choot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

import co.viplove.choot.entity.ChootDocument;
import lombok.extern.slf4j.Slf4j;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChootMongoDbService {

    //@Autowired
    private final GridFSBucket gridFSBucket;

    @Autowired
    MongoDatabaseFactory mongoDatabaseFactory;

    //@Autowired
    private final MongoDatabase mongoDatabase;
    
    private final MongoCollection<Document> filesCollection;

    private final String FILE_COLLECTION = "fs.files";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ChootMongoDbService(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
        this.mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
        this.gridFSBucket = GridFSBuckets.create(mongoDatabase);
        this.filesCollection = mongoDatabase.getCollection(FILE_COLLECTION);
    }

    public ObjectId storeChoot(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(358400)
                    .metadata(new org.bson.Document("content_type", file.getContentType()));
                            //.append("content_type", file.getContentType()));

            return gridFSBucket.uploadFromStream(file.getOriginalFilename(), inputStream, options);
        }
    }

    public InputStream getChoot(ObjectId id) {
        return gridFSBucket.openDownloadStream(id);
    }

    public void deleteChoot(ObjectId id) {
        gridFSBucket.delete(id);
    }

    public void dropDataBase(String dbName){
        mongoDatabaseFactory.getMongoDatabase(dbName).drop();
    }

    public ChootDocument getDocumentById(ObjectId id) {
        //MongoCollection<Document> filesCollection = mongoDatabase.getCollection("fs.files");
        Document query = new Document("_id", id);
        Document doc = filesCollection.find(query).first();
        ChootDocument ChootDocument = null;
        try {
            ChootDocument = objectMapper.readValue(doc.toJson(), ChootDocument.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ChootDocument;
    }

    public List<ChootDocument> getAllChoots() {
        List<ChootDocument> Choots = new ArrayList<>();
        //MongoDatabase database = mongoDatabaseFactory.getMongoDatabase();
        //MongoCollection<Document> filesCollection = mongoDatabase.getCollection("fs.files");
        log.info(filesCollection.toString());
        /* List<Document> documents = filesCollection.find().projection(include("_id")).into(new ArrayList<>());
            documents.forEach(doc -> {
                ChootIds.add(doc.getObjectId("_id"));
                log.info(doc.toJson());
                log.info(doc.getObjectId("_id").toString());
            }
        ); */
        //ObjectMapper objectMapper = new ObjectMapper();
        try (MongoCursor<Document> cursor = filesCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                try {
                    ChootDocument ChootDocument = objectMapper.readValue(doc.toJson(), ChootDocument.class);
                    Choots.add(ChootDocument);
                    log.info(ChootDocument.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }

       
        //List<Document> documents = filesCollection.find().projection(include("_id")).into(new ArrayList<>());

       /*  try (MongoCursor<Document> cursor = filesCollection.find().p.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                ChootIds.add(doc.get("_id"));
            }
        } */

        return Choots;
    }


}
