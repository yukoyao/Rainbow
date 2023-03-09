package org.springframework.springboot.lab.mybatis.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mybatis.annotation.dataobject.UserDO;
import org.springframework.springboot.lab.mybatis.annotation.mapper.UserMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        UserDO userDO = new UserDO().setUsername(UUID.randomUUID().toString()).setPassword("crazy").setCreateTime(new Date());
        int id = userMapper.insert(userDO);
        System.out.println(id);
    }

    @Test
    public void testUpdateById() {
        UserDO updateUser = new UserDO().setId(7).setPassword("pig");
        userMapper.updateById(updateUser);
    }

    @Test
    public void testDeleteById() {
        userMapper.deleteById(7);
    }

    @Test
    public void testSelectById() {
        UserDO userDO = userMapper.selectById(8);
        System.out.println(userDO);
    }

    @Test
    public void testSelectByUsername() {
        userMapper.selectByUsername("c");
    }

    @Test
    public void testSelectByIds() {
        List<UserDO> users = userMapper.selectByIds(Arrays.asList(1, 3));
        System.out.println("users：" + users.size());
    }
}
