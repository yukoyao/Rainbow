package org.rainbow.tokens.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

/**
 * @author K
 */
@Getter
@Setter
public class LoginDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
