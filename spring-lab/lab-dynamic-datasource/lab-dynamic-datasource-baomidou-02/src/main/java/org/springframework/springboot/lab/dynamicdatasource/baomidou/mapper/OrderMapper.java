package org.springframework.springboot.lab.dynamicdatasource.baomidou.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface OrderMapper {

    @DS(DBConstants.DATASOURCE_SLAVE)
    OrderDO selectById(@Param("id") Integer id);

    @DS(DBConstants.DATASOURCE_MASTER)
    int insert(OrderDO entity);
}
