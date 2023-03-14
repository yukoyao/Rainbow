package org.springframework.springboot.lab.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.springboot.lab.rabbitmq.constant.OrderExchangeConstants;
import org.springframework.springboot.lab.rabbitmq.constant.OrderQueueConstants;
import org.springframework.springboot.lab.rabbitmq.constant.RoutingKeyConstants;

/**
 * @author K
 */
@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.host}")
  private String host;

  @Value("${rabbitmq.port}")
  private int port;

  @Value("${rabbitmq.username}")
  private String username;

  @Value("${rabbitmq.password}")
  private String password;

  @Bean
  public ConnectionFactory getFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public AmqpTemplate template(ConnectionFactory factory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public TopicExchange newOrderExchange() {
    return new TopicExchange(OrderExchangeConstants.NEW_ORDER_EXCHANGE);
  }

  @Bean
  public Queue newOrderQueue() {
    return new Queue(OrderQueueConstants.NEW_ORDER_QUEUE);
  }

  @Bean
  public Binding newOrderBinding() {
    return BindingBuilder.bind(newOrderQueue()).to(newOrderExchange()).with(RoutingKeyConstants.NEW_ORDER_ROUTING_KEY);
  }
}
