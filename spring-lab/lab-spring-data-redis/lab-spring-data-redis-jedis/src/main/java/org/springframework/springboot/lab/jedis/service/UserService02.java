package org.springframework.springboot.lab.jedis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.springboot.lab.jedis.cacheobject.UserCacheObject;
import org.springframework.springboot.lab.jedis.dao.redis.UserCacheDao;
import org.springframework.stereotype.Service;

/**
 * @author K
 */
@Service
public class UserService02 {

    @Autowired
    private UserCacheDao userCacheDao;

    public UserCacheObject get(Integer id) {
        return userCacheDao.get(id);
    }

    public void set(Integer id, UserCacheObject object) {
        userCacheDao.set(id, object);
    }
}
