package org.rainbow.security.authentication.up.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.Collections;

/**
 * @author K
 */
public class MfaAuthentication extends AbstractAuthenticationToken {

  private final Authentication first;

  public MfaAuthentication(Authentication first) {
    super(Collections.emptyList());
    this.first = first;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }
}
