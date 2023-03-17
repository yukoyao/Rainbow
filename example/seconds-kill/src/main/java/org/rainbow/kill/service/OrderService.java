package org.rainbow.kill.service;

import org.rainbow.kill.pojo.Stock;

/**
 * @author K
 */
public interface OrderService {

  /**
   * 清空订单表
   */
  int delOrderDBBefore();

  /**
   * 创建订单 (存在超卖问题)
   */
  int createWrongOrder(int sid) throws Exception;

  /**
   * 数据库乐观锁更新库存, 解决超卖问题
   */
  int createOptimisticOrder(int sid) throws Exception;

  /**
   * 数据库乐观锁更新库存, 库存查询 Redis 减少数据库读压力
   */
  int createOrderWithLimitAndRedis(int sid) throws Exception;

  /**
   * 限流 + Redis 缓存库存信息 + Kafka 异步发送消息
   */
  void createOrderWithLimitAndRedisAndKafka(int sid) throws Exception;

  /**
   * Kafka 消费消息
   */
  int consumerTopicToCreateOrderWithKafka(Stock stock) throws Exception;
}
