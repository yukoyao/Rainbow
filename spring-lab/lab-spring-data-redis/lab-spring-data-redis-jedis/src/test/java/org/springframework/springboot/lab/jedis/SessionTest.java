package org.springframework.springboot.lab.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() {
        // 我们在每一个使用 Redis Operations 执行 Redis 操作的时候，每一次都会获取一次 Redis Connection
        // 实际场景中，将会使用 Redis Connection 连接池， 那么在获取的时候会存在一定的竞争。
        // 如果希望在一系列的操作中都使用同一个 Redis Connection 可以使用 Redis 中 SessionCallback

        String result = stringRedisTemplate.execute(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations redisOperations) throws DataAccessException {

                for (int i = 0; i < 100; i++) {
                    redisOperations.opsForValue().set(String.format("animal:%d", i), "rabbit" + i);
                }
                return (String) redisOperations.opsForValue().get(String.format("animal:%d", 0));
            }
        });
        System.out.println("result:" + result);
    }
}
