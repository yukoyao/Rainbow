package org.rainbow.kill.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.rainbow.kill.constants.RedisKeysConstant;
import org.rainbow.kill.dao.StockOrderMapper;
import org.rainbow.kill.pojo.Stock;
import org.rainbow.kill.pojo.StockOrder;
import org.rainbow.kill.redis.StockWithRedis;
import org.rainbow.kill.service.OrderService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 * @author K
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

  @Autowired
  private StockServiceImpl stockService;

  @Autowired
  private StockOrderMapper orderMapper;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Value("${spring.kafka.template.default-topic}")
  private String kafkaTopic;

  @Autowired
  private RedissonClient redissonClient;

  @Override
  public int delOrderDBBefore() {
    return orderMapper.delOrderDBBefore();
  }

  @Override
  public int createWrongOrder(final int sid) throws Exception {
    // 检验库存
    Stock stock = checkStock(sid);
    // 扣减库存
    saleStock(stock);
    // 创建订单
    int res = createOrder(stock);
    return res;
  }

  @Override
  public int createOptimisticOrder(final int sid) throws Exception {
    // 校验库存
    Stock stock = checkStock(sid);
    // 乐观锁更新
    saleStockOptimistic(stock);
    // 创建订单
    int res = createOrder(stock);
    return res;
  }

  @Override
  public int createOrderWithLimitAndRedis(final int sid) throws Exception {
    // 校验库存 从 Redis 中获取
    Stock stock = checkStockWithRedis(sid);
    // 乐观锁更新数据库 和 更新 Redis
    saleStockOptimisticWithRedis(stock);
    // 创建订单
    int res = createOrder(stock);
    return res;
  }

  @Override
  public void createOrderWithLimitAndRedisAndKafka(final int sid) throws Exception {
    // 校验库存 从 Redis 中获取
    Stock stock = checkStockWithRedis(sid);
    // 下单请求发送至 Kafka
    kafkaTemplate.send(kafkaTopic, JSONUtil.toJsonStr(stock));
    log.info("消息发送至 Kafka 成功");
  }

  @Override
  public int consumerTopicToCreateOrderWithKafka(final Stock stock) throws Exception {
    // 乐观锁更新数据库 和 更新 Redis
    saleStockOptimisticWithRedis(stock);
    // 创建订单
    int res = createOrder(stock);
    if (res == 1) {
      log.info("Kafka 消费 Topic 创建订单成功");
    } else {
      log.info("Kafka 消费 Topic 创建订单失败");
    }
    return res;
  }


  /**
   * Redis 校验库存
   */
  private Stock checkStockWithRedis(int sid) throws Exception {
    Integer count = Integer.parseInt(redissonClient.getBucket(RedisKeysConstant.STOCK_COUNT + sid).get().toString());
    Integer sale = Integer.parseInt(redissonClient.getBucket(RedisKeysConstant.STOCK_SALE + sid).get().toString());
    Integer version = Integer.parseInt(
        redissonClient.getBucket(RedisKeysConstant.STOCK_VERSION + sid).get().toString());

    if (count < 1) {
      log.info("库存不足");
      throw new RuntimeException("库存不足 Redis currentCount: " + sale);
    }
    Stock stock = new Stock();
    stock.setId(sid);
    stock.setCount(count);
    stock.setSale(sale);
    stock.setVersion(version);
    stock.setName("手机");

    return stock;
  }

  /**
   * 更新数据库和 redis
   */
  private void saleStockOptimisticWithRedis(Stock stock) throws Exception {
    int res = stockService.updateStockByOptimistic(stock);
    if (res == 0) {
      throw new RuntimeException("并发更新库存失败");
    }
    // 更新 Redis
    StockWithRedis.updateStockWithRedis(stock);
  }

  /**
   * 校验库存
   */
  private Stock checkStock(int sid) throws Exception {
    Stock stock = stockService.getStockById(sid);
    if (stock.getCount() < 0) {
      throw new RuntimeException("库存不足");
    }
    return stock;
  }

  /**
   * 扣库存
   */
  private int saleStock(Stock stock) {
    stock.setSale(stock.getSale() + 1);
    stock.setCount(stock.getCount() - 1);
    return stockService.updateStockById(stock);
  }

  /**
   * 乐观锁扣库存
   */
  private void saleStockOptimistic(Stock stock) throws Exception {
    int count = stockService.updateStockByOptimistic(stock);
    if (count == 0) {
      throw new RuntimeException("并发更新库存失败");
    }
  }

  /**
   * 创建订单
   */
  private int createOrder(Stock stock) throws Exception {
    StockOrder order = new StockOrder();
    order.setSid(stock.getId());
    order.setName(stock.getName());
    order.setCreateTime(new Date());
    int res = orderMapper.insertSelective(order);
    if (res == 0) {
      throw new RuntimeException("创建订单失败");
    }
    return res;
  }

}
