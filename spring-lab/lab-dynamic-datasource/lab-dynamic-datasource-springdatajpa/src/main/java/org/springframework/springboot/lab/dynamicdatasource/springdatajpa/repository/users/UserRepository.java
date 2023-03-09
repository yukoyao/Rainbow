package org.springframework.springboot.lab.dynamicdatasource.springdatajpa.repository.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject.UserDO;

/**
 * @author K
 */
public interface UserRepository extends CrudRepository<UserDO, Integer> {
}
