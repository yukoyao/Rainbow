package org.rainbow.tokens.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author K
 */
@Log4j2
@Component
public class AccessTokenEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(final HttpServletRequest request, final HttpServletResponse response,
      final AuthenticationException authException) throws IOException, ServletException {
    log.error("unauthorized", authException);
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
