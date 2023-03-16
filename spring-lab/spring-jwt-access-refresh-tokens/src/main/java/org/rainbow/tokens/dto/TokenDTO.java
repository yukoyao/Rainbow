package org.rainbow.tokens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author K
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

  private String userId;

  private String accessToken;

  private String refreshToken;

}
