package org.springframework.springboot.lab.jpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;

/**
 * @author K
 */
public interface UserRepository02 extends PagingAndSortingRepository<UserDO, Integer> {
}
