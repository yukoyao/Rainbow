package org.rainbow.excel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author K
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ImportControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testImport() throws Exception {
    InputStream is = ImportControllerTest.class.getClassLoader().getResourceAsStream("product.xls");
    MockMultipartFile file = new MockMultipartFile("file", "product.xls",
        MediaType.TEXT_PLAIN_VALUE, is);

    mockMvc.perform(MockMvcRequestBuilders
            .multipart("/import")
            .file(file))
            //.param("module", "auth"))
        //.contentType(MediaType.APPLICATION_JSON_VALUE)
        //.content(objectMapper.writeValueAsString(registerNewDriverForm)))
        .andDo(print());
  }
}

