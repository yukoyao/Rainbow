package org.rainbow.kill.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rainbow.kill.pojo.Stock;

/**
 * @author K
 */
@Mapper
public interface StockMapper {

  /**
   * 初始化 DB
   */
  @Update("UPDATE stock SET count = 300, sale = 0, version = 0")
  int initDBBefore();

  @Select("SELECT * FROM stock WHERE id = #{id, jdbcType = INTEGER}")
  Stock selectByPrimaryKey(Integer id);

  @Update("UPDATE stock SET count = #{count, jdbcType = INTEGER}, name = #{name, jdbcType = VARCHAR}, "
      + "sale = #{sale,jdbcType = INTEGER}, version = #{version,jdbcType = INTEGER}"
      + " WHERE id = #{id, jdbcType = INTEGER}")
  int updateByPrimaryKeySelective(Stock stock);

  /**
   * 乐观锁 version
   */
  @Update("UPDATE stock SET count = count - 1, sale = sale + 1, version = version + 1 WHERE " +
      "id = #{id, jdbcType = INTEGER} AND version = #{version, jdbcType = INTEGER}")
  int updateByOptimistic(Stock stock);

}
