package org.springframework.springboot.lab.sharding.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.sharding.dataobject.OrderConfigDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface OrderConfigMapper {

    OrderConfigDO selectById(@Param("id") Integer id);
}
