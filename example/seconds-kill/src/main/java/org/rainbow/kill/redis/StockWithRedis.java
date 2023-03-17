package org.rainbow.kill.redis;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.rainbow.kill.constants.RedisKeysConstant;
import org.rainbow.kill.pojo.Stock;
import org.redisson.api.RBucket;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class StockWithRedis {

  public static void updateStockWithRedis(Stock stock) {
    RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);
    RTransaction transaction = redissonClient.createTransaction(TransactionOptions.defaults());
    redissonClient.getAtomicLong(RedisKeysConstant.STOCK_COUNT + stock.getId()).decrementAndGet();
    redissonClient.getAtomicLong(RedisKeysConstant.STOCK_SALE + stock.getId()).incrementAndGet();
    redissonClient.getAtomicLong(RedisKeysConstant.STOCK_VERSION + stock.getId()).incrementAndGet();
    transaction.commit();
  }

  public static void initRedisBefore() {
    RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);
    RTransaction transaction = redissonClient.createTransaction(TransactionOptions.defaults());
    transaction.getBucket(RedisKeysConstant.STOCK_COUNT + 1).set(300);
    transaction.getBucket(RedisKeysConstant.STOCK_SALE + 1).set(0);
    transaction.getBucket(RedisKeysConstant.STOCK_VERSION + 1).set(0);
    transaction.commit();
  }

}
