package org.springframework.springboot.lab.sharding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.sharding.dataobject.OrderDO;
import org.springframework.springboot.lab.sharding.mapper.OrderMapper;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderMapperTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() {
        OrderDO order = orderMapper.selectById(840609944959975424L);
        System.out.println(order);
    }

    @Test
    public void testSelectListByUserId() {
        List<OrderDO> orders = orderMapper.selectListByUserId(1);
        System.out.println(orders.size());
    }

    @Test
    public void testInsert() {
        OrderDO orderDO = new OrderDO();
        // orderDO.setUserId(1);
        // orderDO.setUserId(2);
        // orderDO.setUserId(3);
        // orderDO.setUserId(4);
        // orderDO.setUserId(5);
        // orderDO.setUserId(6);
        // orderDO.setUserId(7);
        // orderDO.setUserId(8);
        orderDO.setUserId(9);
        orderMapper.insert(orderDO);
    }
}
