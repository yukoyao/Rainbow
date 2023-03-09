package org.springframework.springboot.lab.dynamicdatasource.baomidou.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.Application;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject.OrderDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() {
        OrderDO orderDO = orderMapper.selectById(10);
        System.out.println(orderDO);
    }

    @Test
    public void testInsert() {
        OrderDO order = new OrderDO();
        order.setId(10);
        order.setUserId(10);
        orderMapper.insert(order);
    }
}
