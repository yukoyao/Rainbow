package org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.orders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.Application;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.OrderDO;
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
    public void  testInsert() {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(1);
        orderDO.setUserId(1);
        orderMapper.save(orderDO);
    }

    @Test
    public void testSelectById() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }
}
