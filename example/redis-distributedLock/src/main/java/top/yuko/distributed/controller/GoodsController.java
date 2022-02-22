package top.yuko.distributed.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品 controller
 *
 * @author: K
 * @date: 2022/02/22 11:12
 */
@RestController
public class GoodsController {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private Redisson redisson;

  @GetMapping("/buy")
  public String buy(Long goodId) {
    String key = "redisLock";
    RLock redissonLock = redisson.getLock(key);
    redissonLock.lock();

    try {
      String result = this.stringRedisTemplate.opsForValue().get("goods:" + goodId);
      int goodNumber = result == null ? 0 : Integer.parseInt(result);
      if (goodNumber > 0) {
        int realNumber = goodNumber - 1;
        stringRedisTemplate.opsForValue().set("goods:" + goodId, realNumber + "");
        System.out.println("你已经成功秒杀商品，此时还剩余：" + realNumber + "件");
        return "你已经成功秒杀商品，此时还剩余：" + realNumber + "件";
      } else {
        System.out.println("商品已经售罄/活动结束/调用超时，欢迎下次光临");
      }
      return "商品已经售罄/活动结束/调用超时，欢迎下次光临";
    } finally {
      redissonLock.unlock();
    }
  }
}
