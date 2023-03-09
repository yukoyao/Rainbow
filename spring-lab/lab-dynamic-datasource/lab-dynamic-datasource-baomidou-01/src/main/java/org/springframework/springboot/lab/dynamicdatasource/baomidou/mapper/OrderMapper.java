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
@DS(DBConstants.DATASOURCE_ORDERS)
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);
}
