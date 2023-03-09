package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.mapper;

import org.apache.shardingsphere.api.hint.HintManager;
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
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSelectById() {
        // 测试从库的负载均衡
        for (int i = 0; i < 10; i++) {
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
    public void testSelectById02() {
        // 测试强制访问主库
        try(HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
    public void testInsert() {
        OrderDO order = new OrderDO();
        order.setId(20);
        order.setUserId(20);
        orderMapper.insert(order);
    }
}
