package org.springframework.springboot.lab.dynamicdatasource.baomidou.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject.OrderDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAdd() {
        OrderDO order = new OrderDO();
        order.setId(10);
        order.setUserId(10);
        orderService.add(order);
    }

    @Test
    public void testFindById() {
        OrderDO order = orderService.findById(10);
        System.out.println(order);
    }
}
