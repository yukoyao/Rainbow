package org.rainbow.kill.service;

import org.rainbow.kill.pojo.Stock;

/**
 * @author K
 */
public interface StockService {


  /**
   * 根据 id 获取剩余库存
   */
  int getStockCount(Integer id);

  /**
   * 根据 id 查询剩余库存信息
   */
  Stock getStockById(Integer id);

  /**
   * 根据 id 更新库存信息
   */
  int updateStockById(Stock stock);

  /**
   * 乐观锁更新库存, 解决超卖问题
   */
  int updateStockByOptimistic(Stock stock);

  /**
   * 初始化数据库
   */
  int initDBBefore();
}
