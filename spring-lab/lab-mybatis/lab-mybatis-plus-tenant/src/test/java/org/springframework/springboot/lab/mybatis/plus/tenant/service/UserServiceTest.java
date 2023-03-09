package org.springframework.springboot.lab.mybatis.plus.tenant.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mybatis.plus.tenant.Application;
import org.springframework.springboot.lab.mybatis.plus.tenant.context.TenantHolder;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testGetUserAsync() throws ExecutionException, InterruptedException {
        TenantHolder.setTenantId(10);
        Future<UserDO> future = userService.getUserAsync(9);
        future.get();
    }

}
