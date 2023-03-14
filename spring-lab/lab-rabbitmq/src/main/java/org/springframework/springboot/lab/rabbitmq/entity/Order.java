package org.springframework.springboot.lab.rabbitmq.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @author K
 */
@Data
public class Order {

  private Integer id;

  private String code;

  private BigDecimal total;
}
