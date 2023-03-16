package org.rainbow.tokens.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author K
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserRESTTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();


  @Test
  public void testMe() throws Exception {
    this.mockMvc.perform(get("/api/users/me")
            .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY3ODkzNDc2MCwiaWF0IjoxNjc4OTM0NDYwfQ.J6uKxe5_aAh7BKarsvjSQmW83_awnRgln4slXdOCBwA3LL0y6e3T9Rw2wrebtv8OkzTqFMRrc8bonFwUXid2Xw")
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testMe2() throws Exception {
    this.mockMvc.perform(get("/api/users/64118e3afaeb776d01637b36")
            .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY3ODkzNDc2MCwiaWF0IjoxNjc4OTM0NDYwfQ.J6uKxe5_aAh7BKarsvjSQmW83_awnRgln4slXdOCBwA3LL0y6e3T9Rw2wrebtv8OkzTqFMRrc8bonFwUXid2Xw")
        ).andDo(print())
        .andExpect(status().isOk());
  }

}
