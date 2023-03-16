package org.rainbow.tokens.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.rainbow.tokens.dto.LoginDTO;
import org.rainbow.tokens.dto.SignupDTO;
import org.rainbow.tokens.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author K
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthRESTTest {

  @Autowired
  private MockMvc mockMvc;


  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testSignup() throws Exception {
    SignupDTO signupDTO = new SignupDTO();
    signupDTO.setUsername("Jetty");
    signupDTO.setPassword("123456");
    signupDTO.setEmail("jetty@qq.com");

    this.mockMvc.perform(post("/api/auth/signup")
            .content(objectMapper.writeValueAsString(signupDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testLogin() throws Exception {
    LoginDTO loginDTO = new LoginDTO();
    loginDTO.setUsername("Jetty");
    loginDTO.setPassword("123456");

    this.mockMvc.perform(post("/api/auth/login")
            .content(objectMapper.writeValueAsString(loginDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testLogoutAll() throws Exception {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setRefreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJ0b2tlbklkIjoiNjQxMjZmYWE0NTU3MzI1MmJkNmQ4NGY2IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2ODE1MjE4MzQsImlhdCI6MTY3ODkyOTgzNH0.muqJENY2BY8rBdgafBkodMwhM-B2cklWjnPLb5IKhuL8I_ysmctcUGivQP9CKYqZEflQwW_91qISmpAqaoNVAw");

    this.mockMvc.perform(post("/api/auth/logout-all")
            .content(objectMapper.writeValueAsString(tokenDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testAccessToken() throws Exception {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setRefreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJ0b2tlbklkIjoiNjQxMjgwYWY4YWU2ODI1YTVlNmYzY2JhIiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2ODE1MjYxOTEsImlhdCI6MTY3ODkzNDE5MX0.jqxt4lEgikFUr-2qSH1ZfA1UzC0PI8xVgDmNsuWTZ_RJVxbQN79i__l93PSRwsVCIlbyni1_XimACfkloi6ibg");

    this.mockMvc.perform(post("/api/auth/access-token")
            .content(objectMapper.writeValueAsString(tokenDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testRefreshToken() throws Exception {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setRefreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJ0b2tlbklkIjoiNjQxMjgwYWY4YWU2ODI1YTVlNmYzY2JhIiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2ODE1MjYxOTEsImlhdCI6MTY3ODkzNDE5MX0.jqxt4lEgikFUr-2qSH1ZfA1UzC0PI8xVgDmNsuWTZ_RJVxbQN79i__l93PSRwsVCIlbyni1_XimACfkloi6ibg");

    this.mockMvc.perform(post("/api/auth/refresh-token")
            .content(objectMapper.writeValueAsString(tokenDTO))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
  }
}
