package org.springframework.springboot.lab.redisson;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test01() throws IOException {
        // 读取 /resources/lua/compareAndSet.lua 脚本.
        String scriptContents = IOUtils.toString(getClass().getResourceAsStream("/lua/compareAndSet.lua"), "UTF-8");
        // 创建 RedisScript 对象
        RedisScript<Long> script = new DefaultRedisScript<>(scriptContents, Long.class);
        // 执行 lua 脚本
        Long result = stringRedisTemplate.execute(script, Collections.singletonList("redisson:1"), "good", "very good");
        System.out.println(result);
    }

    @Test
    public void test02() throws IOException {
        // 读取 /lua/test.lua 脚本
        String scriptContents = IOUtils.toString(getClass().getResourceAsStream("/lua/test.lua"), "UTF-8");
        // 创建 RedisScript 对象
        RedisScript<List> script = new DefaultRedisScript<>(scriptContents, List.class);
        List result = stringRedisTemplate.execute(script, Arrays.asList("key1", "key2"), "first", "second");
        System.out.println(result);
    }
}
