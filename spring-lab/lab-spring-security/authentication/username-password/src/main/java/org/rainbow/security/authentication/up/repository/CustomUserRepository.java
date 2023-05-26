package org.rainbow.security.authentication.up.repository;

import org.rainbow.security.authentication.up.entity.CustomUser;

/**
 * @author K
 */
public interface CustomUserRepository {

  CustomUser findCustomUserByEmail(String email);
}
