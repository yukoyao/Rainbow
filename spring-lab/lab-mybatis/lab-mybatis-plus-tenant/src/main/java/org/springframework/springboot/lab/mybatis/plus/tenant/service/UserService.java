package org.springframework.springboot.lab.mybatis.plus.tenant.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserDO;
import org.springframework.springboot.lab.mybatis.plus.tenant.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author K
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Async
    public Future<UserDO> getUserAsync(Integer id) {
        UserDO userDO = userMapper.selectById(id);
        log.info("[getUserAsync][id({}) user({})]", id, userDO);
        return AsyncResult.forValue(userDO);
    }
}
