package org.rainbow.tokens.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author K
 */
@Getter
@Setter
public class SignupDTO {

  @NotBlank
  @Size(min = 3, max = 30)
  private String username;

  @NotBlank
  @Size(max = 60)
  private String email;

  @NotBlank
  @Size(min = 6, max = 60)
  private String password;
}
