package org.springframework.springboot.lab.jpa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepository01Test {

    @Autowired
    private UserRepository01 userRepository01;

    @Test
    public void testSave() {
        UserDO user = new UserDO().setUsername(UUID.randomUUID().toString())
                .setPassword("wuhu~").setCreateTime(new Date());
        userRepository01.save(user);
    }

    @Test
    public void testSave2() {
        for (int i = 0; i < 10; i++) {
            UserDO userDO = new UserDO().setUsername(UUID.randomUUID().toString()).setPassword("user" + i).setCreateTime(new Date());
            userRepository01.save(userDO);
        }
    }

    @Test
    public void testUpdate() {
        // 先查询一条记录
        Optional<UserDO> userDO = userRepository01.findById(1);
        Assert.isTrue(userDO.isPresent(), "记录不能为空");
        // 更新一条记录
        UserDO updateUser = userDO.get();
        updateUser.setPassword("After modify");
        userRepository01.save(updateUser);
    }

    @Test
    public void testDelete() {
        userRepository01.deleteById(2);
    }

    @Test
    public void testSelectById() {
        Optional<UserDO> userDO = userRepository01.findById(1);
        System.out.println(userDO.get());
    }

    @Test
    public void testSelectByIds() {
        Iterable<UserDO> users = userRepository01.findAllById(Arrays.asList(1, 4));
        users.forEach(System.out::println);
    }
}
