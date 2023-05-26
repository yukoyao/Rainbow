package org.rainbow.security.authentication.up.repository;

import org.rainbow.security.authentication.up.entity.CustomUser;
import java.util.Map;

/**
 * @author K
 */
public class MapCustomUserRepository implements CustomUserRepository {

  private final Map<String, CustomUser> emailToCustomUser;

  public MapCustomUserRepository(Map<String, CustomUser> emailToCustomUser) {
    this.emailToCustomUser = emailToCustomUser;
  }

  @Override
  public CustomUser findCustomUserByEmail(final String email) {
    return this.emailToCustomUser.get(email);
  }
}
