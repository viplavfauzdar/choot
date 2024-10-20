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

import co.viplove.choot.controller.ChootController;
import co.viplove.choot.entity.ChootDocument;
import co.viplove.choot.service.ChootService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChootController.class)
public class ChootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChootService chootService;

    @Test
    public void testUploadChoot() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());
        ObjectId id = new ObjectId();
        when(chootService.storeChoot(any())).thenReturn(id);

        mockMvc.perform(multipart("/choot/upload").file(file))
            .andExpect(status().isOk())
            .andExpect(content().string(id.toHexString()));
    }

    @Test
    public void testGetChoot() throws Exception {
        ObjectId id = new ObjectId();
        ChootDocument chootDocument = new ChootDocument();
        chootDocument.setFilename("test.jpeg");
        ChootDocument.Metadata metadata = chootDocument.new Metadata();
        metadata.setContentType("image/jpeg");
        chootDocument.setMetadata(metadata); // Create an instance of the inner class using the outer class instance
        InputStream chootStream = new ByteArrayInputStream("Test content".getBytes());

        when(chootService.getDocumentById(id)).thenReturn(chootDocument);
        when(chootService.getChoot(id)).thenReturn(chootStream);

        mockMvc.perform(get("/choot/" + id.toHexString()))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.jpeg\""))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "image/jpeg"))
            .andExpect(content().bytes("Test content".getBytes()));
    }
}
