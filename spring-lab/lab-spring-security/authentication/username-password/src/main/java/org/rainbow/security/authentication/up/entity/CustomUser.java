package org.rainbow.security.authentication.up.entity;

import lombok.Data;

/**
 * @author K
 */
@Data
public class CustomUser {

  private long id;

  private String email;

  private String password;

  private String secret;

  private String answer;

}
