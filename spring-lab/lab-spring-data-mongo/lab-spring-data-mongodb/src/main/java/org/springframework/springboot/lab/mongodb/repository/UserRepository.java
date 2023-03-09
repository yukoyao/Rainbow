package org.springframework.springboot.lab.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.springboot.lab.mongodb.dataobject.UserDO;

/**
 * @author K
 */
public interface UserRepository extends MongoRepository<UserDO, Integer> {
}
