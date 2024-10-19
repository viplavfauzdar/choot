package co.viplove.choot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import co.viplove.choot.config.MongoConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(MongoConfig.class)
public class MongoDbTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testInsertAndFind() {
        
        // Create a sample document
        SampleDocument sampleDocument = new SampleDocument("2", "Sample Data");

        // Insert the document into MongoDB
        mongoTemplate.insert(sampleDocument, "sampleCollection");

        // Query the document from MongoDB
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        SampleDocument retrievedDocument = mongoTemplate.findOne(query, SampleDocument.class, "sampleCollection");

        // Assert the document is retrieved correctly
        assertThat(retrievedDocument).isNotNull();
        assertThat(retrievedDocument.getId()).isEqualTo("1");
        assertThat(retrievedDocument.getData()).isEqualTo("Sample Data");
    }

    // Sample document class
    static class SampleDocument {
        private String id;
        private String data;

        public SampleDocument(String id, String data) {
            this.id = id;
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public String getData() {
            return data;
        }
    }
}