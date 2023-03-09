package org.springframework.springboot.lab.dynamicdatasource.springdatajpa.repository.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.Application;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSelectById() {
        UserDO user = userRepository.findById(1)
                .orElse(null); // 为空，则返回 null
        System.out.println(user);
    }

}
