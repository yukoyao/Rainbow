package org.rainbow.kill.kafka;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rainbow.kill.pojo.Stock;
import org.rainbow.kill.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author K
 */
@Slf4j
@Component
public class ConsumerListener {

  @Resource
  private OrderService orderService;

  @KafkaListener(topics = "SECONDS-KILL-TOPIC")
  public void listen(ConsumerRecord<String, String> record) throws Exception {
    Optional<String> kafkaMessage = Optional.ofNullable(record.value());
    String message = StrUtil.toString(kafkaMessage.get());
    Stock stock = JSONUtil.toBean(message, Stock.class);
    // 创建订单
    try {
      orderService.consumerTopicToCreateOrderWithKafka(stock);
    } catch (Exception e) {
      log.info(e.getMessage());
    }

  }
}
