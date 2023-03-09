package org.springframework.springboot.lab.jpa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepository04Test {

    @Autowired
    private UserRepository04 userRepository;

    @Test
    public void testFindByUsername01() {
        UserDO user = userRepository.findByUsername01("00902145-0380-4234-9153-4c04446a31f4");
        System.out.println(user);
    }

    @Test
    public void testFindByUsername02() {
        UserDO user = userRepository.findByUsername02("00902145-0380-4234-9153-4c04446a31f4");
        System.out.println(user);
    }

    @Test
    public void testFindByUsername03() {
        UserDO user = userRepository.findByUsername03("00902145-0380-4234-9153-4c04446a31f4");
        System.out.println(user);
    }

    @Test
    @Transactional // 在单元测试中, 事务默认回滚.
    public void testUpdateUsernameById() {
        userRepository.updateUsernameById(5, "test");
    }
}
