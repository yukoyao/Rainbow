package org.rainbow.tokens.repository;

import org.bson.types.ObjectId;
import org.rainbow.tokens.document.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author K
 */
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

  void deleteByOwner_Id(Object id);

  default void deleteByOwner_Id(String id) {
    deleteByOwner_Id(new ObjectId(id));
  };
}
