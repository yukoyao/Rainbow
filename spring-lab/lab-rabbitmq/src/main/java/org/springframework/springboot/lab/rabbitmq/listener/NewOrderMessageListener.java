package org.springframework.springboot.lab.rabbitmq.listener;

import cn.hutool.core.util.StrUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.springboot.lab.rabbitmq.constant.OrderExchangeConstants;
import org.springframework.springboot.lab.rabbitmq.constant.OrderQueueConstants;
import org.springframework.springboot.lab.rabbitmq.constant.RoutingKeyConstants;
import org.springframework.springboot.lab.rabbitmq.entity.Order;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Component
public class NewOrderMessageListener {

  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = OrderQueueConstants.NEW_ORDER_QUEUE, durable = "true"),
      exchange = @Exchange(value = OrderExchangeConstants.NEW_ORDER_EXCHANGE, type = ExchangeTypes.TOPIC),
      key = RoutingKeyConstants.NEW_ORDER_ROUTING_KEY
  ))
  public void consumeNewOrderMessage(@Payload Order order) {
    System.out.println(StrUtil.format("Receiver msg : {}", order));
  }
}
