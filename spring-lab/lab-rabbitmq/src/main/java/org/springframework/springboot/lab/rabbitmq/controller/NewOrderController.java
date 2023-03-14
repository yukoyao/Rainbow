package org.springframework.springboot.lab.rabbitmq.controller;

import org.springframework.springboot.lab.rabbitmq.service.NewOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author K
 */
@RestController
public class NewOrderController {

  @Resource
  private NewOrderService newOrderService;

  @GetMapping("/send")
  public String send() {
    newOrderService.publishNewOrderMessage();
    return "ok";
  }
}
