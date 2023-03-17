package org.rainbow.kill.controller;

import lombok.extern.slf4j.Slf4j;
import org.rainbow.kill.redis.StockWithRedis;
import org.rainbow.kill.redis.limit.RedisLimit;
import org.rainbow.kill.service.OrderService;
import org.rainbow.kill.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K
 */
@Slf4j
@RestController
public class IndexController {

  private static final String success = "SUCCESS";
  private static final String error = "ERROR";

  @Autowired
  private OrderService orderService;

  @Autowired
  private StockService stockService;

  @Autowired
  private RedisLimit redisLimit;

  /**
   * 初始化数据库和缓存
   */
  @GetMapping("initDBAndRedisBefore")
  public String initDBAndRedisBefore() {
    int res = 0;
    try {
      // 初始化库存信息
      res = stockService.initDBBefore();
      // 清空订单表
      res &= (orderService.delOrderDBBefore() == 0 ? 1 : 0);
      // 重置缓存
      StockWithRedis.initRedisBefore();
    } catch (Exception e) {
      log.error("Exception: ", e);
    }
    if (res == 1) {
      log.info("重置数据库和缓存成功！");
    }
    return res == 1 ? success : error;
  }

  /**
   * 秒杀基本逻辑, 存在超卖问题
   */
  @GetMapping("createWrongOrder")
  public String createWrongOrder(int sid) {
    int res = 0;
    try {
      res = orderService.createWrongOrder(sid);
    } catch (Exception e) {
      log.error("Exception: ", e);
    }
    return res == 1 ? success : error;
  }

  /**
   * 乐观锁扣库存
   */
  @GetMapping("createOptimisticOrder")
  public String createOptimisticOrder(int sid) {
    int res = 0;
    try {
      res = orderService.createOptimisticOrder(sid);
    } catch (Exception e) {
      log.error("Exception: " + e);
    }
    return res == 1 ? success : error;
  }

  /**
   * 限流 + 乐观锁更新
   */
  @GetMapping("createOptimisticLimitOrder")
  public String createOptimisticLimitOrder(int sid) {
    int res = 0;
    try {
      if (redisLimit.limit()) {
        res = orderService.createOptimisticOrder(sid);
      }
    } catch (Exception e) {
      log.error("Exception: " + e);
    }
    return res == 1 ? success : error;
  }

  /**
   * 限流 + Redis 缓存库存 减少 DB 压力
   */
  @GetMapping("createOrderWithLimitAndRedis")
  public String createOrderWithLimitAndRedis(int sid) {
    int res = 0;
    try {
      if (redisLimit.limit()) {
        res = orderService.createOrderWithLimitAndRedis(sid);
        if (res == 1) {
          log.info("秒杀成功");
        }
      }
    } catch (Exception e) {
      log.error("Exception: " + e);
    }
    return res == 1 ? success : error;
  }

  /**
   * 限流 + Redis 缓存库存 + 异步落库
   */
  @GetMapping("createOrderWithLimitAndRedisAndKafka")
  public String createOrderWithLimitAndRedisAndKafka(int sid) {
    try {
      if (redisLimit.limit()) {
        orderService.createOrderWithLimitAndRedisAndKafka(sid);
      }
    } catch (Exception e) {
      log.error("Exception: " + e);
    }
    return "秒杀请求正在处理，排队中";
  }
}
