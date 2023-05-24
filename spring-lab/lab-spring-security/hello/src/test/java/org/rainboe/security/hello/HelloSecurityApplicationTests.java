package org.rainboe.security.hello;

import org.junit.jupiter.api.Test;
import org.rainbow.security.hello.HelloApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author K
 */
@SpringBootTest(classes = HelloApplication.class)
@AutoConfigureMockMvc
public class HelloSecurityApplicationTests {


  @Autowired
  private MockMvc mockMvc;


  @Test
  void indexWhenUnAuthenticationThenRedirect() throws Exception {
    this.mockMvc.perform(get("/")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void indexWhenAuthenticationThenOk() throws Exception {
    this.mockMvc.perform(get("/")).andExpect(status().isOk());
  }
}
