package org.springframework.springboot.lab.rabbitmq.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.springboot.lab.rabbitmq.constant.OrderExchangeConstants;
import org.springframework.springboot.lab.rabbitmq.constant.RoutingKeyConstants;
import org.springframework.springboot.lab.rabbitmq.entity.Order;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author K
 */
@Service
public class NewOrderService {

  @Resource
  private RabbitTemplate rabbitTemplate;

  public void publishNewOrderMessage() {
    Order order = new Order();
    order.setId(RandomUtil.randomInt());
    order.setCode(RandomUtil.randomString(8));
    order.setTotal(RandomUtil.randomBigDecimal());

    rabbitTemplate.convertAndSend(OrderExchangeConstants.NEW_ORDER_EXCHANGE, RoutingKeyConstants.NEW_ORDER_ROUTING_KEY, order);
  }

}
