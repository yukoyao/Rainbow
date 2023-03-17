package org.rainbow.kill.runner;

import org.rainbow.kill.constants.RedisKeysConstant;
import org.rainbow.kill.pojo.Stock;
import org.rainbow.kill.service.StockService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author K
 */
@Component
public class RedisPreheatRunner implements ApplicationRunner {

  @Resource
  private StockService stockService;

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public void run(final ApplicationArguments args) throws Exception {
    // 从数据库查询库存
    Stock stock = stockService.getStockById(1);

    // 删除旧缓存
    stringRedisTemplate.delete(RedisKeysConstant.STOCK_COUNT + stock.getCount());
    stringRedisTemplate.delete(RedisKeysConstant.STOCK_SALE + stock.getSale());
    stringRedisTemplate.delete(RedisKeysConstant.STOCK_VERSION + stock.getVersion());

    // 缓存预热
    Integer sid = stock.getId();
    stringRedisTemplate.opsForValue().set(RedisKeysConstant.STOCK_COUNT + sid, String.valueOf(stock.getCount()));
    stringRedisTemplate.opsForValue().set(RedisKeysConstant.STOCK_SALE + sid, String.valueOf(stock.getSale()));
    stringRedisTemplate.opsForValue().set(RedisKeysConstant.STOCK_VERSION + sid, String.valueOf(stock.getVersion()));
  }
}
