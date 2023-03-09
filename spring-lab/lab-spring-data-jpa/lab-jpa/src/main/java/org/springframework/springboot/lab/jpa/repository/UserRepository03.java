package org.springframework.springboot.lab.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;

import java.util.Date;

/**
 * @author K
 */
public interface UserRepository03 extends PagingAndSortingRepository<UserDO, Integer> {

    UserDO findByUsername(String username);

    Page<UserDO> findByCreateTimeAfter(Date createTime, Pageable pageable);
}
