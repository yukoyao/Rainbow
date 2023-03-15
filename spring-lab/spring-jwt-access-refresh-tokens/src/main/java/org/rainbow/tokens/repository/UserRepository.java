package org.rainbow.tokens.repository;

import org.rainbow.tokens.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * @author K
 */
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByUsername(String username);
}
