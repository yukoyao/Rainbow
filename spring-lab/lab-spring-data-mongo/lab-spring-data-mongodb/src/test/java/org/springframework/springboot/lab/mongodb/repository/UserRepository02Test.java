package org.springframework.springboot.lab.mongodb.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.springboot.lab.mongodb.Application;
import org.springframework.springboot.lab.mongodb.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepository02Test {

    @Autowired
    private UserRepository02 userRepository;

    @Test
    public void testFindByName() {
        UserDO user = userRepository.findByUsername("小嘎嘎");
        System.out.println(user);
    }

    @Test
    public void testFindByNameLike() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = PageRequest.of(0, 10, sort);
        Page<UserDO> page = userRepository.findByUsernameLike("嘎嘎", pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
    }

}
