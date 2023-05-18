package org.rainbow.security.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义 AuthenticationProvider，用于验证码登录场景
 *
 * @author K
 */
public class KaptchaAuthenticationProvider extends DaoAuthenticationProvider {

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String kaptcha = req.getParameter("kaptcha");
    String sessionKaptcha = (String) req.getSession().getAttribute("kaptcha");
    if (kaptcha != null && sessionKaptcha != null && kaptcha.equalsIgnoreCase(sessionKaptcha)) {
      return super.authenticate(authentication);
    }
    throw new AuthenticationServiceException("验证码输入错误");
  }
}
