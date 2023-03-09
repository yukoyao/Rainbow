package org.springframework.springboot.lab.mongodb.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mongodb.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepository03Test {

    @Autowired
    private UserRepository03 userRepository03;

    @Test
    public void testFindByUsername01() {
        UserDO user = userRepository03.findByUsername01("小嘎嘎");
        System.out.println(user);
    }

    @Test
    public void testFindByUsernameLike01() {
        UserDO userDO = userRepository03.findByUsernameLike01("嘎嘎");
        System.out.println(userDO);
    }
}
