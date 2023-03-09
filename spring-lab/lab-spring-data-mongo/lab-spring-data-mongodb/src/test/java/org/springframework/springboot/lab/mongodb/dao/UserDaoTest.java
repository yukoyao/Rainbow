package org.springframework.springboot.lab.mongodb.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mongodb.Application;
import org.springframework.springboot.lab.mongodb.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        UserDO userDO = new UserDO();
        userDO.setId(1);
        userDO.setUsername("小嘎嘎");
        userDO.setPassword("buzhidao");

        UserDO.Profile profile = new UserDO.Profile();
        profile.setNickname("小鸭鸭");
        profile.setGender(1);
        userDO.setProfile(profile);
        userDao.insert(userDO);
    }

    // 这里要注意，如果使用 save 方法来更新的话，必须是全量字段，否则其它字段会被覆盖。
    // 所以，这里仅仅是作为一个示例。
    @Test // 更新一条记录
    public void testUpdate() {
        // 创建 UserDO 对象
        UserDO updateUser = new UserDO();
        updateUser.setId(1);
        updateUser.setUsername("nicai");

        // 执行更新
        userDao.updateById(updateUser);
    }

    @Test // 根据 ID 编号，删除一条记录
    public void testDelete() {
        userDao.deleteById(1);
    }

    @Test
    public void testSelectById() {
        UserDO userDO = userDao.findById(1);
        System.out.println(userDO);
    }

    @Test
    public void testSelectByIds() {
        List<UserDO> users = userDao.findAllById(Arrays.asList(1, 4));
        users.forEach(System.out::println);
    }
}
