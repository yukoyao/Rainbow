package org.springframework.springboot.lab.mybatis.plus.tenant.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mybatis.plus.tenant.Application;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserDO;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserProfileDO;
import org.springframework.springboot.lab.mybatis.plus.tenant.vo.UserDetailVO;
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

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Test
    public void initTableData() {
        // 清理数据
        userMapper.delete(new QueryWrapper<>());
        userProfileMapper.delete(new QueryWrapper<>());

        // 插入一个用户
        UserDO userDO = new UserDO().setUsername(UUID.randomUUID().toString())
                .setPassword("hello").setCreateTime(new Date())
                .setDeleted(0);
        userMapper.insert(userDO);

        // 插入用户扩展信息
        UserProfileDO userProfileDO = new UserProfileDO();
        userProfileDO.setUserId(userDO.getId());
        userProfileDO.setGender(1);
        userProfileDO.setTenantId(10);
        userProfileDO.setDeleted(0);
        userProfileMapper.insert(userProfileDO);
    }

    @Test
    public void testInsert() {
        UserDO user = new UserDO().setUsername(UUID.randomUUID().toString())
                .setPassword("world").setCreateTime(new Date()).setDeleted(0);
        userMapper.insert(user);
    }

    @Test
    public void testUpdateById() {
        UserDO updateUser = new UserDO().setId(1).setPassword("not hello");
        userMapper.updateById(updateUser);
    }

    @Test
    public void testDeleteById() {
        userMapper.deleteById(1);
    }

    @Test
    public void testSelectByUsername() {
        UserDO userDO = userMapper.selectByUsername("not hello");
        System.out.println(userDO);
    }

    @Test
    public void testSelectByIds() {
        List<UserDO> users = userMapper.selectByIds(Arrays.asList(1, 3));
        System.out.println("users:" + users.size());
    }

    @Test
    public void testSelectListA() {
        List<UserDetailVO> list = userMapper.selectListA();
        System.out.println(list);
    }

    @Test
    public void testSelectListB() {
        List<UserDetailVO> list = userMapper.selectListB();
        System.out.println(list);
    }
}
