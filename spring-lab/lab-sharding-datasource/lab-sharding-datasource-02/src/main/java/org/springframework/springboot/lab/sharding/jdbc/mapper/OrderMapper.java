package org.springframework.springboot.lab.sharding.jdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.springboot.lab.sharding.jdbc.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}
