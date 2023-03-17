package org.rainbow.kill.redis.limit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author K
 */
@SpringBootTest
public class RedisLimitTest {

  @Autowired
  private RedisLimit redisLimit;

  @Test
  public void testLimit() {
    for (int i = 0; i < 100; i++) {
      Boolean limit = redisLimit.limit();
      System.out.println(limit);
    }
  }
}
