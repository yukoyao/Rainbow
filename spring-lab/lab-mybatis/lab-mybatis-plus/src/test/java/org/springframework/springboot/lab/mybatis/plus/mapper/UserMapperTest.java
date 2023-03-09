package org.springframework.springboot.lab.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mybatis.plus.Application;
import org.springframework.springboot.lab.mybatis.plus.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
        UserDO user = new UserDO().setUsername(UUID.randomUUID().toString()).setPassword("nicai")
                .setCreateTime(new Date()).setDeleted(0);
        userMapper.insert(user);
    }

    @Test
    public void testUpdateById() {
        UserDO updateUser = new UserDO().setId(9)
                .setPassword("wobucai");
        userMapper.updateById(updateUser);
    }

    @Test
    public void testDeleteById() {
        userMapper.deleteById(9);
    }

    @Test
    public void testSelectById() {
        UserDO userDO = userMapper.selectById(9);
        System.out.println(userDO);
    }

    @Test
    public void testSelectByUsername() {
        UserDO userDO = userMapper.selectByUsername("wobucai");
        System.out.println(userDO);
    }

    @Test
    public void testSelectByIds() {
        List<UserDO> users = userMapper.selectByIds(Arrays.asList(8, 9));
        System.out.println("user:" + users.size());
    }

    @Test
    public void testSelectPageByCreateTime() {
        IPage<UserDO> page = new Page<>(1, 10);
        Date createTime = new Date(2023 - 1900, Calendar.FEBRUARY, 16); // 临时 Demo ，实际不建议这么写
        page = userMapper.selectPageByCreateTime(page, createTime);
        System.out.println("users：" + page.getRecords().size());
    }


}
