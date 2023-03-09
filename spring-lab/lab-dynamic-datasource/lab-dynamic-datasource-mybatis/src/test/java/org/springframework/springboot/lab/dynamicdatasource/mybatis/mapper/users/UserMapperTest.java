package org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.Application;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }
}
