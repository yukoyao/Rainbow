package org.springframework.springboot.lab.redis.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.springboot.lab.redis.unittest.config.TestRedisConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class Test01 {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test01() {
        // 写入
        stringRedisTemplate.opsForValue().set("yunai", "shuai");
        // 读取
        String value = stringRedisTemplate.opsForValue().get("yunai");
        Assert.assertEquals("值不匹配", "shuai", value);

        // 测试重启后读取
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return "";
        });
    }
}
