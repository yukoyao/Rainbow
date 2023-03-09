package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.Application;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dataobject.OrderDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testSelectById() {
        OrderDO order = orderDao.selectById(1);
        System.out.println(order);
    }
}
