package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dataobject;

import lombok.Data;
import lombok.ToString;

/**
 * 订单 DO
 *
 * @author K
 */
@Data
@ToString
public class OrderDO {

    /**
     * 订单编号
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer userId;
}
