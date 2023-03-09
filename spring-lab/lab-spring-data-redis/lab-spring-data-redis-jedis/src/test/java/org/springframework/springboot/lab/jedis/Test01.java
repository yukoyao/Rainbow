package org.springframework.springboot.lab.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.springboot.lab.jedis.cacheobject.UserCacheObject;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test01 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringSetKey() {
        stringRedisTemplate.opsForValue().set("rabbit", "cute");
    }

    @Test
    public void testStringSetKey02() {
        redisTemplate.opsForValue().set("rabbit", "cute");
    }

    @Test
    public void testSetAdd() {
        stringRedisTemplate.opsForSet().add("rabbit_descriptions", "cute", "white");
    }

    @Test
    public void testStringSetKeyUserCache() {
        UserCacheObject rabbit = UserCacheObject.builder().id(1).name("rabbit").gender(1).build();
        String key = String.format("user:%d", rabbit.getId());
        redisTemplate.opsForValue().set(key, rabbit);
    }

    @Test
    public void testStringGetKeyUserCache() {
        String key = String.format("user:%d", 1);
        UserCacheObject value = (UserCacheObject) redisTemplate.opsForValue().get(key);
        System.out.println(value);
    }
}
