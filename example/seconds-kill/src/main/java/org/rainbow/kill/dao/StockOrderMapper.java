package org.rainbow.kill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.rainbow.kill.pojo.StockOrder;

/**
 * @author K
 */
@Mapper
public interface StockOrderMapper {

  @Insert("INSERT INTO stock_order (id, sid, name, create_time) VALUES " +
      "(#{id, jdbcType = INTEGER}, #{sid, jdbcType = INTEGER}, #{name, jdbcType = VARCHAR}, #{createTime, jdbcType = TIMESTAMP})")
  int insertSelective(StockOrder order);

  /**
   * 清空订单表
   * 成功为 0，失败为 -1
   */
  @Update("TRUNCATE TABLE stock_order")
  int delOrderDBBefore();

}
