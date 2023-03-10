package org.springframework.springboot.lab.sharding.jdbc.mapper;

import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.sharding.jdbc.Application;
import org.springframework.springboot.lab.sharding.jdbc.dataobject.OrderDO;
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
        for (int i = 0; i < 2; i++) {
            OrderDO orderDO = orderMapper.selectById(1);
            System.out.println(orderDO);
        }
    }

    @Test
    public void testSelectById02() {
        // 强制访问主库
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            OrderDO orderDO = orderMapper.selectById(1);
            System.out.println(orderDO);
        }
    }

    @Test
    public void testInsert() {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(10);
        orderMapper.insert(orderDO);
    }
}
