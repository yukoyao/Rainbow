package org.springframework.springboot.lab.sharding.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.sharding.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author K
 */
@Repository
public interface OrderMapper {

    OrderDO selectById(@Param("id") Long id);

    List<OrderDO> selectListByUserId(@Param("userId") Integer userId);

    void insert(OrderDO order);
}
