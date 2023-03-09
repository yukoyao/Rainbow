package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.Application;
import org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.dataobject.OrderDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAdd() {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(1);
        orderDO.setUserId(1);
        orderService.add(orderDO);
        System.out.println(orderDO);
    }

    @Test
    public void testFindById() {
        OrderDO order = orderService.findById(1);
        System.out.println(order);
    }
}
