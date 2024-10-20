package co.viplove.choot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import co.viplove.choot.controller.ChootMongoDbController;
import co.viplove.choot.entity.ChootMongoDbDocument;
import co.viplove.choot.service.ChootMongoDbService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChootMongoDbController.class)
public class ChootMongoDbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChootMongoDbService chootMongoDbService;

    @Test
    public void testUploadChoot() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());
        ObjectId id = new ObjectId();
        when(chootMongoDbService.storeChoot(any())).thenReturn(id);

        mockMvc.perform(multipart("/choot/upload").file(file))
            .andExpect(status().isOk())
            .andExpect(content().string(id.toHexString()));
    }

    @Test
    public void testGetChoot() throws Exception {
        ObjectId id = new ObjectId();
        ChootMongoDbDocument chootMongoDbDocument = new ChootMongoDbDocument();
        chootMongoDbDocument.setFilename("test.jpeg");
        ChootMongoDbDocument.Metadata metadata = chootMongoDbDocument.new Metadata();
        metadata.setContentType("image/jpeg");
        chootMongoDbDocument.setMetadata(metadata); // Create an instance of the inner class using the outer class instance
        InputStream chootStream = new ByteArrayInputStream("Test content".getBytes());

        when(chootMongoDbService.getDocumentById(id)).thenReturn(chootMongoDbDocument);
        when(chootMongoDbService.getChoot(id)).thenReturn(chootStream);

        mockMvc.perform(get("/choot/" + id.toHexString()))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.jpeg\""))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/jpeg"))
            .andExpect(content().bytes("Test content".getBytes()));
    }
}
