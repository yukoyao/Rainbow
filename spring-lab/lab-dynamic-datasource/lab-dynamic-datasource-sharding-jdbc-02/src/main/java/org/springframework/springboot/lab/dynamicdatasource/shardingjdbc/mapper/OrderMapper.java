package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);

    int insert(OrderDO entity);
}
