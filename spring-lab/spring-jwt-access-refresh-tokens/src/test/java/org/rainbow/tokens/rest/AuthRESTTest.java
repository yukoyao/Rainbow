package org.rainbow.tokens.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.rainbow.tokens.dto.LoginDTO;
import org.rainbow.tokens.dto.SignupDTO;
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
            .header("accessToken", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY3ODg3MjQyMywiaWF0IjoxNjc4ODcyMTIzfQ.bU46-6KtZWoxryNka2R1Jg1Raom_nny1kD4tAfu16WXxch7a0vjSaMSAuvm3CrFsbRUN8p1xi6DilajbdyhFlw")
            .header("refreshToken", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NDExOGUzYWZhZWI3NzZkMDE2MzdiMzYiLCJ0b2tlbklkIjoiNjQxMThlM2FmYWViNzc2ZDAxNjM3YjM3IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2ODE0NjQxMjMsImlhdCI6MTY3ODg3MjEyM30.JWjt9wxN9TgCIGGinsXGOsTtobXM4oJxSaQvFk2kx_zz_GyGi6z7_vvuIQrBEC6MZYVvpEGHPvQ-78cDsUgtiw")
        ).andDo(print())
        .andExpect(status().isOk());
  }
}
