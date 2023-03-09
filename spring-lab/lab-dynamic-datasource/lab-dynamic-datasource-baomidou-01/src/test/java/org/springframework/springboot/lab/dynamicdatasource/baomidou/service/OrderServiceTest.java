package org.springframework.springboot.lab.dynamicdatasource.baomidou.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.Application;
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
    public void test01() {
        orderService.method01();
    }

    @Test
    public void test02() {
        orderService.method02();
    }

    @Test
    public void test03() {
        orderService.method03();
    }

    @Test
    public void test04() {
        orderService.method04();
    }

    @Test
    public void test05() {
        orderService.method05();
    }
}
