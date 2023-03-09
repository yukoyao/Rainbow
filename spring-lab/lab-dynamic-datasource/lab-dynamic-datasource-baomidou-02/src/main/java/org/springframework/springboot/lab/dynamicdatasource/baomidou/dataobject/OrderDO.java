package org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject;

import lombok.Data;
import lombok.ToString;

/**
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
