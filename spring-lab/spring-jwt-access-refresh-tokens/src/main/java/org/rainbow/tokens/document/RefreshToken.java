package org.rainbow.tokens.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * @author K
 */
@Document
@Data
public class RefreshToken {

  @Id
  String id;

  @DocumentReference(lazy = true)
  private User owner;

}
