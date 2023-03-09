package org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.orders;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface OrderMapper {

    void save(OrderDO orderDO);

    OrderDO selectById(@Param("id") Integer id);
}
