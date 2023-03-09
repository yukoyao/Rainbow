package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.Application;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSelectById() {
        UserDO user = userDao.selectById(1);
        System.out.println(user);
    }

}
