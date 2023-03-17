package org.rainbow.kill.service.impl;

import org.rainbow.kill.dao.StockMapper;
import org.rainbow.kill.pojo.Stock;
import org.rainbow.kill.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author K
 */
@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private StockMapper stockMapper;


  @Override
  public int getStockCount(final Integer id) {
    Stock stock = stockMapper.selectByPrimaryKey(id);
    return stock.getCount();
  }

  @Override
  public Stock getStockById(final Integer id) {
    return stockMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateStockById(final Stock stock) {
    return stockMapper.updateByPrimaryKeySelective(stock);
  }

  @Override
  public int updateStockByOptimistic(final Stock stock) {
    return stockMapper.updateByOptimistic(stock);
  }

  @Override
  public int initDBBefore() {
    return stockMapper.initDBBefore();
  }
}
