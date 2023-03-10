package org.springframework.springboot.lab.sharding.jdbc.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * 订单 DO
 *
 * @author K
 */
@Data
@ToString
@TableName(value = "orders")
public class OrderDO {

    private Long id;

    private Integer userId;
}
