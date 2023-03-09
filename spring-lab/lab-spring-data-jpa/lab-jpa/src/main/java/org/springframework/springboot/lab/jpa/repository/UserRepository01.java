package org.springframework.springboot.lab.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;

/**
 * @author K
 */
public interface UserRepository01 extends CrudRepository<UserDO, Integer> {
}
