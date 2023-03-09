package org.springframework.springboot.lab.sharding.dataobject;

import lombok.Data;
import lombok.ToString;

/**
 * 订单配置 DO
 *
 * @author K
 */
@Data
@ToString
public class OrderConfigDO {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 支付超时时间
     *
     * 单位：分钟
     */
    private Integer payTimeout;
}
